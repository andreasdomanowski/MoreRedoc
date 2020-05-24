package moreredoc.project.data;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
/**
 * Represents decomposed sentences. This is necessary, because if the new subsentences are just appended to the whole processed text, the entity count will be misleading.
 *
 */
public class DecomposedSentenceTripel {
	private String originalSentence;
	private List<String> subsentences = new ArrayList<>();
	private String originalWithoutPartSentences;

	private static final Logger logger = Logger.getLogger(DecomposedSentenceTripel.class);

	public DecomposedSentenceTripel() {
	}

	public DecomposedSentenceTripel(String originalSentence, List<String> subsentences,
			String originalWithoutPartSentences) {
		this.originalSentence = originalSentence;
		this.subsentences = subsentences;
		this.originalWithoutPartSentences = originalWithoutPartSentences;
	}

	public String getOriginalSentence() {
		return originalSentence;
	}

	public void setOriginalSentence(String originalSentence) {
		this.originalSentence = originalSentence;
	}

	public List<String> getSubsentences() {
		return subsentences;
	}

	public String getOriginalWithoutPartSentences() {
		return originalWithoutPartSentences;
	}

	public void setOriginalWithoutPartSentences(String originalWithoutPartSentences) {
		this.originalWithoutPartSentences = originalWithoutPartSentences;
	}
	
	public void addSubentence(String subsentence) {
		this.subsentences.add(subsentence);
	}
	
	public void prettyPrint() {
		logger.info("Original sentence:\n\t" + this.originalSentence);
		
		if(!subsentences.isEmpty()) {
			
			logger.info("Original without subsentences:\n\t " + this.originalWithoutPartSentences);
			logger.info("Subsentences:");
			for(String s : this.subsentences) {
				logger.info("\t " + s);
			}
		}
		
	}


}
