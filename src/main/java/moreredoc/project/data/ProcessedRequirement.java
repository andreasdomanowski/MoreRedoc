package moreredoc.project.data;

import java.util.List;
import java.util.Set;

import moreredoc.linguistics.processing.CorefReplacementService;
import moreredoc.linguistics.processing.OpenIEService;
import moreredoc.linguistics.processing.SentenceDecomposerService;
import moreredoc.linguistics.processing.SentenceRegularizerService;
import org.apache.log4j.Logger;

/*
 * Wrapper class for Requirements which represents processed (coref, normalized) requirements
 */
public class ProcessedRequirement {
	private static final Logger logger = Logger.getLogger(ProcessedRequirement.class);

	private Requirement wrappedRequirement;
	private String corefResolvedRegularizedText;
	private List<DecomposedSentenceTripel> decomposedSentences;
	private List<RelationTripleWrapper> relationTriples;
	private String processedText;

	/**
	 * Hide default constructor, because a processed requirement needs to process
	 * the requirement This is done via the constructor to ensure that this has
	 * happened
	 */
	@SuppressWarnings("unused")
	private ProcessedRequirement() {
		// no-op, won't be called!
	}

	public ProcessedRequirement(Requirement requirement) {
		this.wrappedRequirement = requirement;
		this.corefResolvedRegularizedText = processText();
		this.decomposedSentences = decomposeSentences();
		this.processedText = generateProcessedText();
		this.relationTriples = OpenIEService.performIE(processedText);
	}

	/**
	 * Generates a string containing all processed (normalization, coref,
	 * decomposition) sentences of the requirement.
	 * 
	 * @return Processed text of the requirement
	 */
	private String generateProcessedText() {
		StringBuilder textBuilder = new StringBuilder();

		// append whole resolved text
		textBuilder.append(" " + this.corefResolvedRegularizedText + " ");

		// append decomposed sentences
		for (DecomposedSentenceTripel t : this.decomposedSentences) {
			// append original sentence without part sentences
			if (t.getOriginalWithoutPartSentences() != null)
				textBuilder.append(" " + t.getOriginalWithoutPartSentences() + " ");

			for (String s : t.getSubsentences()) {
				if (s != null) {
					textBuilder.append(" " + s);
				}
			}
		}

		return textBuilder.toString();
	}

	public String getId() {
		return this.wrappedRequirement.getId();
	}

	public Set<String> getKeywords() {
		return this.wrappedRequirement.getKeywords();
	}

	public String getUnprocessedText() {
		return this.wrappedRequirement.getUnprocessedText();
	}

	public String getCorefResolvedRegularizedText() {
		return this.corefResolvedRegularizedText;
	}

	public List<DecomposedSentenceTripel> getDecomposedSentences() {
		return decomposedSentences;
	}

	private List<DecomposedSentenceTripel> decomposeSentences() {
		return SentenceDecomposerService.decomposeSBARSentences(corefResolvedRegularizedText);
	}

	public String getProcessedText() {
		return processedText;
	}

	private String processText() {
		String toReturn = "";

		// sanitize string step 1 - remove keyword indicators
		// but not yet the stopwords they are important for coref resolution
		toReturn = SentenceRegularizerService.removeKeywordIndicators(getUnprocessedText());

		// do coref replacement
		toReturn = CorefReplacementService.resolveCoreferences(toReturn);

		// sanitize string step 2 - remove stopwords
		toReturn = SentenceRegularizerService.removeStopwords(toReturn);

		return toReturn;
	}

	public List<RelationTripleWrapper> getRelationTriples() {
		return relationTriples;
	}

	public String toString() {
		return this.wrappedRequirement.toString() + "\n\t processed text: " + this.processedText;
	}

	public void printIE() {
		for (RelationTripleWrapper triple : this.relationTriples) {
			logger.info("New Triple for sentence \"" + triple.getSentence() + "\"");
			logger.info(("\tConfidence: " + triple.getTriple().confidence));
			logger.info(("\tSubject: " + triple.getTriple().subjectGloss()));
			logger.info(("\tRelation: " + triple.getTriple().relationGloss()));
			logger.info(("\tObject: " + triple.getTriple().objectGloss()));
		}
	}

}
