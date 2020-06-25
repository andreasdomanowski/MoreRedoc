package moreredoc.project.data;

import moreredoc.application.exceptions.InvalidRequirementInputException;
import moreredoc.datainput.InputDataHandler;
import moreredoc.linguistics.processing.WordRegularizerService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.*;

public class MoreRedocProject {
    private static final Logger logger = Logger.getLogger(MoreRedocProject.class);

    private InputDataHandler inputDataHandler;

    // Set of all concepts
    private Set<String> projectDomainConcepts = new HashSet<>();
    private List<Requirement> projectRequirements;
    private List<ProcessedRequirement> processedProjectRequirements = new ArrayList<>();
    private Map<String, Integer> conceptCount = new HashMap<>();

    private String wholeText;
    private String wholeCorefResolvedRegularizedText;
    private String wholeProcessedText;

    /*
     * Hide default constructor because a project has to be initialized with
     * requirements
     */
    @SuppressWarnings("unused")
    private MoreRedocProject() {
        throw new UnsupportedOperationException();
    }

    public MoreRedocProject(String csvPathKeywords, String csvPathText,
            InputDataHandler dataHandler) throws InvalidRequirementInputException {
        Objects.requireNonNull(csvPathKeywords);
        Objects.requireNonNull(csvPathText);
        Objects.requireNonNull(dataHandler);

        this.inputDataHandler = dataHandler;

        List<Requirement> requirementsFromCsvInputs = dataHandler.getRequirementsFromCsvInputs(csvPathKeywords, csvPathText);
        Objects.requireNonNull(requirementsFromCsvInputs);

        this.projectRequirements = requirementsFromCsvInputs;

        // process requirements via the ProcessedRequirement constructor
        for (Requirement r : projectRequirements) {
            processedProjectRequirements.add(new ProcessedRequirement(r));
        }

        // after initializing the processed requirements, all texts of the project
        // requirements can be generated
        this.wholeText = generateText();
        this.wholeCorefResolvedRegularizedText = generateCorefResolvedRegularizedText();
        this.wholeProcessedText = generateNormalizedAndDecomposedCorefResolvedText();

        // calculate occurrences of keywords
        initializeDomainConcepts();

        // add additional concepts
        inputDataHandler.getAdditionalDomainConcepts(csvPathKeywords).forEach(word -> this.projectDomainConcepts.add(WordRegularizerService.regularize(word)));
    }

    /**
     * Generates a string containing all processed (normalization, coref,
     * decomposition) requirement texts
     *
     * @return Processed Text for the whole Project
     */
    private String generateNormalizedAndDecomposedCorefResolvedText() {
        StringBuilder textBuilder = new StringBuilder();

        for (ProcessedRequirement r : this.getProcessedProjectRequirements()) {
            textBuilder.append(r.getProcessedText());
        }

        return textBuilder.toString();
    }

    private String generateCorefResolvedRegularizedText() {
        StringBuilder textBuilder = new StringBuilder();

        for (ProcessedRequirement r : this.getProcessedProjectRequirements()) {
            textBuilder.append(r.getCorefResolvedRegularizedText());
        }

        return textBuilder.toString();
    }

    private String generateText() {
        StringBuilder textBuilder = new StringBuilder();

        for (Requirement r : this.getProjectRequirements()) {
            textBuilder.append(r.getUnprocessedText());
        }

        return textBuilder.toString();
    }

    private void initializeDomainConcepts(){
        for (String concept : projectDomainConcepts) {
            conceptCount.put(concept, 1);
        }

        for (ProcessedRequirement r : processedProjectRequirements) {
            // concatenate processed text
            // regularize strings, put in set
            for (String s : r.getKeywords()) {
                String regularizedConcept = WordRegularizerService.regularize(s);
                projectDomainConcepts.add(regularizedConcept);

                int occurrences;

                // count occurences
                // initialize value for count
                if (conceptCount.get(regularizedConcept) == null) {
                    occurrences = 1;
                } else {
                    occurrences = conceptCount.get(regularizedConcept) + 1;
                }

                conceptCount.put(regularizedConcept, occurrences);
            }
        }
        // normalize each word of whole processed text
        // split by whitespaces via regex
        String[] splitProcessedText = StringUtils.split(wholeCorefResolvedRegularizedText);
        for (int i = 0; i < splitProcessedText.length; i++) {
            splitProcessedText[i] = WordRegularizerService.regularize(splitProcessedText[i]);
        }

        // count domain concept occurrences in regularized text
        // has to be done via iterating over splitted text, otherwise infixes will be counted
        for (String s : projectDomainConcepts) {
            int count = conceptCount.get(s);

            for (String value : splitProcessedText) {
                if (value.equals(s))
                    count++;
            }

            conceptCount.put(s, count);
        }

    }

    public String getWholeText() {
        return wholeText;
    }

    public String getWholeCorefResolvedRegularizedText() {
        return wholeCorefResolvedRegularizedText;
    }

    public String getWholeProcessedText() {
        return wholeProcessedText;
    }

    public Set<String> getProjectDomainConcepts() {
        return projectDomainConcepts;
    }

    public List<Requirement> getProjectRequirements() {
        return projectRequirements;
    }

    public List<ProcessedRequirement> getProcessedProjectRequirements() {
        return processedProjectRequirements;
    }

    public Map<String, Integer> getEntityCount() {
        return conceptCount;
    }

    public void printEntityCount() {
        Set<String> keySet = conceptCount.keySet();
        for (String s : keySet) {
            logger.info("Keyword: " + s);
            logger.info("\tabsolute: " + conceptCount.get(s));
            logger.info("\trelative: " + getRelativeFrequencyOfKeyword(s));
        }
    }

    public double getRelativeFrequencyOfKeyword(String keyword) {
        int totalCount = conceptCount.keySet().size();
        int occurrences = conceptCount.get(keyword);

        return (double) occurrences / totalCount;
    }
}
