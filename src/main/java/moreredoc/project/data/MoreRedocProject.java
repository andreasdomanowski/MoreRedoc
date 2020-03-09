package moreredoc.project.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import moreredoc.linguistics.processing.WordRegularizerService;

public class MoreRedocProject {
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

	public MoreRedocProject(List<Requirement> projectRequirements) throws Exception {
		this.projectRequirements = projectRequirements;

		// process requirements via the ProcessedRequirement constructor
		for (Requirement r : projectRequirements) {
			processedProjectRequirements.add(new ProcessedRequirement(r));
		}

		// after initializing the processed requirements, all texts of the project
		// requirements can be generated
		this.wholeText = generateText();
		this.wholeCorefResolvedRegularizedText = generateCorefResolvedRegularizedText();
		this.wholeProcessedText = generateWholeProcessedText();

		// calculate occurences of keywords
		initializeDomainConcepts();

	}

	/**
	 * Generates a string containing all processed (normalization, coref, decomposition) requirement texts
	 * @return Processed Text for the whole Project
	 */
	private String generateWholeProcessedText() {
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

	private void initializeDomainConcepts() {
		String wholeProcessedText = "";

		for (ProcessedRequirement r : processedProjectRequirements) {
			// concatenate processed text
			wholeProcessedText = wholeProcessedText + r.getCorefResolvedRegularizedText();
			// regularize strings, put in set
			for (String s : r.getKeywords()) {
				String regularizedConcept = WordRegularizerService.regularize(s);
				projectDomainConcepts.add(regularizedConcept);

				int occurences;

				// count occurences
				// initialize value for count
				if (conceptCount.get(regularizedConcept) == null) {
					occurences = 1;
				} else {
					occurences = conceptCount.get(regularizedConcept) + 1;
				}

				conceptCount.put(regularizedConcept, occurences);
			}

		}
		// normalize each word of whole processed text
		// split by whitespaces via regex
		String[] splitProcessedText = StringUtils.split(wholeProcessedText);
		String regularizedWholeProcessedText = "";
		for (int i = 0; i < splitProcessedText.length; i++) {
			splitProcessedText[i] = WordRegularizerService.regularize(splitProcessedText[i]);
		}

		// count domain concept occurences in regularized text
		// has to be done this way, otherwise it will count infixes..
		for (String s : projectDomainConcepts) {
			int count = conceptCount.get(s);

			for (int i = 0; i < splitProcessedText.length; i++) {
				if (splitProcessedText[i].equals(s))
					count++;
			}

			conceptCount.put(s, count);
		}

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
			System.out.println("Keyword: " + s);
			System.out.println("\tabsolute: " + conceptCount.get(s));
			System.out.println("\trelative: " + getRelativeFrequencyOfKeyword(s));
		}
	}

	public double getRelativeFrequencyOfKeyword(String keyword) {
		int totalCount = conceptCount.keySet().size();
		int occurences = conceptCount.get(keyword);

		return (double) occurences / totalCount;
	}
}
