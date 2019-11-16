package moreredoc.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import edu.stanford.nlp.ie.KBPRelationExtractor.RelationType;
import edu.stanford.nlp.ie.util.RelationTriple;
import moreredoc.analysis.data.PossessionTuple;
import moreredoc.analysis.data.VerbCandidate;
import moreredoc.analysis.services.AttributiveRelationshipService;
import moreredoc.analysis.services.CompoundAnalysisService;
import moreredoc.analysis.services.VerbAnalyzerService;
import moreredoc.project.data.MoreRedocProject;
import moreredoc.project.data.ProcessedRequirement;
import moreredoc.project.data.RelationTripleWrapper;
import moreredoc.umldata.UmlClass;
import moreredoc.umldata.UmlModel;
import moreredoc.umldata.UmlRelationship;
import moreredoc.umldata.UmlRelationshipType;

public class MoreRedocAnalysis {
	private MoreRedocAnalysisConfiguration configuration;

	private MoreRedocProject project;
	private List<PossessionTuple> possessionTuples;

	// model parts
	private UmlModel model;
	private Map<String, UmlClass> classMapping = new HashMap<>();
	private List<UmlRelationship> relationships = new ArrayList<>();

	/**
	 * Hide constructor with no arguments.
	 */
	@SuppressWarnings("unused")
	private MoreRedocAnalysis() {

	}

	public MoreRedocAnalysis(MoreRedocProject project, MoreRedocAnalysisConfiguration configuration) {
		this.project = project;
		this.setConfiguration(configuration);
		initializePossessionTuples();
		initializeClasses();
		generateModel();
		analyzeVerbs();
	}

	private void initializePossessionTuples() {
		possessionTuples = new ArrayList<>();
		// add compound concepts from domain concept set
		possessionTuples
				.addAll(CompoundAnalysisService.computeCompoundTypeOfConcept(project.getProjectDomainConcepts()));

		// add possession tuples from attribute relationships
		possessionTuples.addAll(AttributiveRelationshipService
				.computeRelationshipTuples(project.getWholeProcessedText(), project.getProjectDomainConcepts()));

		// add possesion tuples from compound analysis
		for (ProcessedRequirement r : project.getProcessedProjectRequirements()) {
			// analyze every subject and every object from the triples
			for (RelationTripleWrapper w : r.getRelationTriples()) {
				RelationTriple triple = w.getTriple();
				String subject = triple.subjectGloss();
				String object = triple.objectGloss();

				for (String s : project.getProjectDomainConcepts()) {
					for (PossessionTuple tuple : CompoundAnalysisService.computeCompoundType(subject, s,
							project.getProjectDomainConcepts())) {
						possessionTuples.add(tuple);
					}

					for (PossessionTuple tuple : CompoundAnalysisService.computeCompoundType(object, s,
							project.getProjectDomainConcepts())) {
						possessionTuples.add(tuple);
					}
				}

			}
		}
	}

	private void initializeClasses() {
		// iterate over all domain concepts, check whether it is class or attribute
		for (String domainConcept : project.getProjectDomainConcepts()) {
			boolean isClassCandidate = false;
			boolean isAttributeCandidate = false;

			// possible owner class for an attribute
			String ownerClassName = null;

			// iterate over all possession tuples, check for occurences indicating either
			// class or attribute type
			// if domain concept is a subject in a tuple, it will be a candidate for a class
			// if its an object, it will be a candidate for an attribute
			for (PossessionTuple tuple : this.possessionTuples) {
				if (tuple.getOwner().equals(domainConcept))
					isClassCandidate = true;
				if (tuple.getOwned().equals(domainConcept)) {
					isAttributeCandidate = true;
					ownerClassName = tuple.getOwner();
				}
			}

			// if its just class candidate -> class
			if (isClassCandidate && !isAttributeCandidate && !this.classMapping.containsKey(domainConcept)) {
				UmlClass newClass = new UmlClass(domainConcept);
				this.classMapping.put(domainConcept, newClass);
			}
			// if its just attribute candidate -> attribute to respective class
			if (!isClassCandidate && isAttributeCandidate) {
				UmlClass classForAttribute;
				if (this.classMapping.containsKey(ownerClassName)) {
					classForAttribute = this.classMapping.get(ownerClassName);
					classForAttribute.addAttribute(domainConcept);
				} else {
					classForAttribute = new UmlClass(ownerClassName);
					classForAttribute.addAttribute(domainConcept);
					this.classMapping.put(ownerClassName, classForAttribute);
				}
			}
			// if its class AND attribute candidate, it will be a class, but theres an
			// aggregration between to classes
			if (isClassCandidate && isAttributeCandidate) {
				// class representing the attribute
				UmlClass attributeClass = null;
				if (this.classMapping.containsKey(domainConcept)) {
					attributeClass = this.classMapping.get(domainConcept);
				} else {
					attributeClass = new UmlClass(domainConcept);
					this.classMapping.put(domainConcept, attributeClass);
				}
				// class representing the parent class of the attribute
				UmlClass ownerClass;
				if (this.classMapping.containsKey(ownerClassName)) {
					ownerClass = this.classMapping.get(ownerClassName);
				} else {
					ownerClass = new UmlClass(ownerClassName);
					this.classMapping.put(ownerClassName, ownerClass);
				}
				UmlRelationship newRelationship = new UmlRelationship(ownerClass, attributeClass,
						UmlRelationshipType.AGGREGATION, null);
				this.relationships.add(newRelationship);
			}

			if (!isClassCandidate && !isAttributeCandidate && !this.classMapping.containsKey(domainConcept)) {
				UmlClass newClass;
				newClass = new UmlClass(domainConcept);
				this.classMapping.put(domainConcept, newClass);
			}

		}
	}

	private void analyzeVerbs() {
		if (this.model == null)
			return;

		List<VerbCandidate> verbList = new ArrayList<>();

		for (ProcessedRequirement r : project.getProcessedProjectRequirements()) {
			verbList.addAll(
					VerbAnalyzerService.analyzeIETriples(r.getRelationTriples(), project.getProjectDomainConcepts()));
		}

		for (VerbCandidate c : verbList) {
			String currentFrom = c.getFrom();
			if (this.classMapping.containsKey(currentFrom)) {
				UmlClass currentFromClass = this.classMapping.get(currentFrom);
				
				StringBuilder methodStringBuilder = new StringBuilder(c.getVerb());

				// if getTo != null, check if it is modelled as class
				// if so, add relationship
				// if not, just add it as method argument
				if (c.getTo() != null) {
					if (this.classMapping.containsKey(c.getTo())) {
						// TODO 
						UmlClass currentToClass = this.classMapping.get(c.getTo());
						UmlRelationship newRelationship = new UmlRelationship(currentFromClass, currentToClass,
								UmlRelationshipType.ASSOCIATION, c.getVerb());
						model.getRelationships().add(newRelationship);

					}
					methodStringBuilder.append("(" + c.getTo() + ")");

				}

				currentFromClass.addMethod(methodStringBuilder.toString());
			}
		}

	}

	private void generateModel() {
		this.model = (new UmlModel(classMapping, relationships));
	}

	public List<PossessionTuple> getPossessionTuples() {
		return this.possessionTuples;
	}

	public UmlModel getModel() {
		return model;
	}

	public MoreRedocAnalysisConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(MoreRedocAnalysisConfiguration configuration) {
		this.configuration = configuration;
	}

}
