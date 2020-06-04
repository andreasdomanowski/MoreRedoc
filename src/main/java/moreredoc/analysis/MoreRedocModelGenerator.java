package moreredoc.analysis;

import edu.stanford.nlp.ie.util.RelationTriple;
import moreredoc.analysis.data.PossessionTuple;
import moreredoc.analysis.data.VerbCandidate;
import moreredoc.analysis.services.AttributiveRelationshipService;
import moreredoc.analysis.services.CompoundAnalysisService;
import moreredoc.analysis.services.VerbAnalyzerService;
import moreredoc.linguistics.processing.Commons;
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

    private Set<PossessionTuple> possessionTuples;

    // model parts
    private UmlModel model;
    private final Map<String, UmlClass> classMapping = new HashMap<>();
    private final List<UmlRelationship> relationships = new ArrayList<>();

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

    public UmlModel generateModel() {
        logger.info("Analysis started");

        aggregatePossessionTuples();
        logger.info("\tPossession tuples initialized");

        initializeClasses();
        logger.info("\tClasses initialized");

        initializeModel();
        logger.info("\tUML Model initialized");

        analyzeVerbs();
        logger.info("\tAnalysis of verbs initialized");

        initializeMultiplicities();
        logger.info("\tMultiplicities initialized");

        if (configuration.getCropEmptyClasses()) {
            cropEmptyClasses();
        }

        logger.info("Analysis done");

        return model;
    }

    private void aggregatePossessionTuples() {
        possessionTuples = new HashSet<>();
        // add compound concepts from domain concept set
        possessionTuples
                .addAll(CompoundAnalysisService.computeCompoundConceptsFromAllConcepts(project.getProjectDomainConcepts()));

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
                    possessionTuples.addAll(CompoundAnalysisService.computeCompoundConceptsFromText(subject, s,
                            project.getProjectDomainConcepts()));

                    possessionTuples.addAll(CompoundAnalysisService.computeCompoundConceptsFromText(object, s,
                            project.getProjectDomainConcepts()));
                }
            }
        }
    }

    private void initializeClasses() {
        Set<String> classCandidates = new HashSet<>();
        // maps from attribute to respective class
        // e.g. customer_number -> customer
        Map<String, List<String>> attributeCandidates = new HashMap<>();

        // iterate over all domain concepts, check whether it is class or attribute
        for (String domainConcept : project.getProjectDomainConcepts()) {
            boolean isClassCandidate = false;
            boolean isAttributeCandidate = false;

            // possible owner class for an attribute
            List<String> ownerClassNames = new ArrayList<>();

            // iterate over all possession tuples, check for occurrences indicating either
            // class or attribute type
            // if domain concept is a subject in a tuple, it will be a candidate for a class
            // if its an object, it will be a candidate for an attribute
            for (PossessionTuple tuple : this.possessionTuples) {
                if (tuple.getOwner().equals(domainConcept))
                    isClassCandidate = true;
                if (tuple.getOwned().equals(domainConcept)) {
                    isAttributeCandidate = true;
                    ownerClassNames.add(tuple.getOwner());
                }
            }

            if (isClassCandidate) {
                classCandidates.add(domainConcept);
            }
            if (isAttributeCandidate) {
                attributeCandidates.put(domainConcept, ownerClassNames);
            }
        }

        // generate classes for each class candidate
        classCandidates.forEach(classCandidate -> this.classMapping.put(classCandidate, new UmlClass(classCandidate)));

        attributeCandidates.forEach((attributeCandidate, clazz) -> {
            // if attribute candidate is a class candidate too, generate aggregation from its owner to the candidate
            if (classCandidates.contains(attributeCandidate)) {
                for(String owner : clazz){
                    UmlRelationship newRelationship = new UmlRelationship(this.classMapping.get(owner), this.classMapping.get(attributeCandidate),
                            UmlRelationshipType.AGGREGATION, null, null);
                    this.relationships.add(newRelationship);
                }
                // if attribute is no class candidate, put it in the respective class as an attribute
            }else{
                for(String owner : clazz){
                    UmlClass ownerClass = this.classMapping.get(owner);
                    ownerClass.addAttribute(attributeCandidate);
                }
            }
        });

        // if a concept is not flagged as class candidate or attribute candidate, model it as class to make it cropable by tool/user
        this.project.getProjectDomainConcepts().forEach(concept -> {
            if (!classCandidates.contains(concept) && !attributeCandidates.containsKey(concept)) {
                this.classMapping.put(concept, new UmlClass(concept));
            }
        });
    }

    private void initializeModel() {
        this.model = new UmlModel(classMapping, relationships);
    }

    private void analyzeVerbs() {
        Objects.requireNonNull(model);
        Objects.requireNonNull(configuration);

        List<VerbCandidate> verbList = new ArrayList<>();

        project.getProcessedProjectRequirements().forEach(r -> verbList.addAll(
                VerbAnalyzerService.analyzeIETriples(r.getRelationTriples(), project.getProjectDomainConcepts())));

        for (VerbCandidate c : verbList) {
            String currentFrom = c.getFrom();
            if (this.classMapping.containsKey(currentFrom) && !Commons.VERBS_TO_NOT_MODEL_WHEN_ALONE.contains(c.getVerb())) {
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

                if (configuration.getModelVerbsAsMethods()) {
                    currentFromClass.addMethod(methodStringBuilder.toString());
                }
            }
        }
    }

    private void initializeMultiplicities() {
        for (PossessionTuple tuple : possessionTuples) {
            for (UmlRelationship relationship : relationships) {
                if (tuple.getOwner().equals(relationship.getFrom().getName()) && tuple.getOwned().equals(relationship.getTo().getName())) {
                    relationship.setMultiplicity(tuple.getMultiplicity());
                }
            }
        }
    }

    private void cropEmptyClasses() {
        List<UmlClass> attributeAndMethodlessClasses = new ArrayList<>();
        List<UmlClass> classesToRemove = new ArrayList<>();

        // put all classes without attribute or methods in respective container
        model.getAllClassesImmutable().stream().filter(c -> c.getAttributes().isEmpty() && c.getMethods().isEmpty()).forEach(attributeAndMethodlessClasses::add);

        // check every candidate for being in a relationship with another class
        // if there is no relationship, the class is to be removed
        attributeAndMethodlessClasses.stream().filter(c -> {
            // iterate over relationships
            // if no relationship contains c, return true, else return false
            for (UmlRelationship r : model.getRelationships()) {
                if (r.getTo().equals(c) || r.getFrom().equals(c)) {
                    return false;
                }
            }
            return true;
        }).forEach(classesToRemove::add);

        // remove classes from mapping in model
        classesToRemove.forEach(classToRemove -> this.model.getNameToClassMapping().entrySet().removeIf(x -> x.getValue().equals(classToRemove)));
    }

    public Set<PossessionTuple> getPossessionTuples() {
        return this.possessionTuples;
    }

    public MoreRedocAnalysisConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(MoreRedocAnalysisConfiguration configuration) {
        this.configuration = configuration;
    }

}
