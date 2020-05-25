package moreredoc.analysis;

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
import org.apache.log4j.Logger;

import java.util.*;

public class MoreRedocModelGenerator {
    private static final Logger logger = Logger.getLogger(MoreRedocModelGenerator.class);

    private MoreRedocProject project;
    private MoreRedocAnalysisConfiguration configuration;

    private List<PossessionTuple> possessionTuples;

    // model parts
    private UmlModel model;
    private Map<String, UmlClass> classMapping = new HashMap<>();
    private List<UmlRelationship> relationships = new ArrayList<>();

    /**
     * Hide constructor with no arguments.
     */
    @SuppressWarnings("unused")
    private MoreRedocModelGenerator() {

    }

    public MoreRedocModelGenerator(MoreRedocProject project, MoreRedocAnalysisConfiguration configuration) {
        this.project = project;
        this.setConfiguration(configuration);
    }


    private void initializePossessionTuples() {
        possessionTuples = new ArrayList<>();
        // add compound concepts from domain concept set
        possessionTuples
                .addAll(CompoundAnalysisService.computeCompoundTypeOfConcept(project.getProjectDomainConcepts()));

        // add possession tuples from attribute relationships
        possessionTuples.addAll(AttributiveRelationshipService
                .computeRelationshipTuples(project.getWholeProcessedText(), project.getProjectDomainConcepts()));

        // add possession tuples from compound analysis
        for (ProcessedRequirement r : project.getProcessedProjectRequirements()) {
            // analyze every subject and every object from the triples
            for (RelationTripleWrapper w : r.getRelationTriples()) {
                RelationTriple triple = w.getTriple();
                String subject = triple.subjectGloss();
                String object = triple.objectGloss();

                for (String s : project.getProjectDomainConcepts()) {
                    possessionTuples.addAll(CompoundAnalysisService.computePossessionTuples(subject, s,
                            project.getProjectDomainConcepts()));

                    possessionTuples.addAll(CompoundAnalysisService.computePossessionTuples(object, s,
                            project.getProjectDomainConcepts()));
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

            // iterate over all possession tuples, check for occurrences indicating either
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
            // if its class AND attribute candidate, it will be a class, but there's an
            // aggregation between these two classes
            if (isClassCandidate && isAttributeCandidate) {
                // class representing the attribute
                UmlClass attributeClass;
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
                        UmlRelationshipType.AGGREGATION, null, null);
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
        Objects.requireNonNull(model);
        Objects.requireNonNull(configuration);

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
                    if (configuration.getModelVerbsAsRelationships() && this.classMapping.containsKey(c.getTo())) {
                        UmlClass currentToClass = this.classMapping.get(c.getTo());
                        UmlRelationship newRelationship = new UmlRelationship(currentFromClass, currentToClass,
                                UmlRelationshipType.ASSOCIATION, c.getVerb(), null);
                        model.getRelationships().add(newRelationship);

                    }
                    methodStringBuilder.append("(").append(c.getTo()).append(")");

                }

                if(configuration.getModelVerbsAsMethods()){
                    currentFromClass.addMethod(methodStringBuilder.toString());
                }
            }
        }
    }

    private void initializeMultiplicities() {
        for(PossessionTuple tuple : possessionTuples){
            System.out.println(tuple);
            for(UmlRelationship relationship : relationships){
                System.out.println(relationship);
                if(tuple.getOwner().equals(relationship.getFrom()) && tuple.getOwned().equals(relationship.getTo())){
                    relationship.setMultiplicity(tuple.getMultiplicity());
                }
            }
        }
    }

    private void initializeModel() {
        this.model = new UmlModel(classMapping, relationships);
    }

    public List<PossessionTuple> getPossessionTuples() {
        return this.possessionTuples;
    }

    public UmlModel generateModel() {
        logger.info("Analysis started");

        initializePossessionTuples();
        logger.info("\tPossession tuples initialized");

        initializeClasses();
        logger.info("\tClasses initialized");

        initializeModel();
        logger.info("\tUML Model initialized");

        analyzeVerbs();
        logger.info("\tAnalysis of verbs initialized");

        initializeMultiplicities();
        logger.info("\tMultiplicities initialized");

        logger.info("Analysis done");

        return model;
    }

    public MoreRedocAnalysisConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(MoreRedocAnalysisConfiguration configuration) {
        this.configuration = configuration;
    }

}
