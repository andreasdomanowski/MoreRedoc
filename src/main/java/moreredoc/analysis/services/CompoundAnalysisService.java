package moreredoc.analysis.services;

import moreredoc.analysis.data.PossessionTuple;
import moreredoc.linguistics.processing.MoreRedocStringUtils;
import moreredoc.linguistics.processing.SentenceRegularizerService;
import moreredoc.linguistics.processing.WordRegularizerService;
import moreredoc.umldata.Multiplicity;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CompoundAnalysisService {
	private static Logger logger = Logger.getLogger(CompoundAnalysisService.class);

	private CompoundAnalysisService() {
	}

	/**
	 * Computes the compound type (Enumeration CompoundType, either) of a given
	 * domain concept for a given sentence
	 * 
	 * @param in
	 * @param domainConceptToTest
	 * @param domainConcepts
	 * @return
	 */
	public static List<PossessionTuple> computePossessionTuples(String in, String domainConceptToTest,
			Set<String> domainConcepts) {
		List<PossessionTuple> toReturn = new ArrayList<>();
		// domain concept can be attribute or/AND class type, initialize flags for this
		// combinations
		@SuppressWarnings("unused")
		boolean isAttributeType = false;
		@SuppressWarnings("unused")
		boolean isClassType = false;

		// regularized domain concept to test
		domainConceptToTest = WordRegularizerService.regularize(domainConceptToTest);

		// split input string by whitespace, read into array
		String[] inputSplittedByWhitespace = StringUtils.split(in.trim());

		// normalize every word in input string/array
		for (int i = 0; i < inputSplittedByWhitespace.length; i++) {
			inputSplittedByWhitespace[i] = WordRegularizerService.regularize(inputSplittedByWhitespace[i]);
		}

		// list containing every index of a occurence in the array
		List<Integer> occurenceIndices = MoreRedocStringUtils.getIndicesForMatches(inputSplittedByWhitespace,
				domainConceptToTest);

		int maxIndex = inputSplittedByWhitespace.length - 1;

		// iterate once again, check neighbourhood of occurences
		for (int occurenceIndex : occurenceIndices) {
			// compute indices of words in input before and after occurence of domain
			// concept
			int preOccurence = occurenceIndex - 1;
			int postOccurence = occurenceIndex + 1;

			// check, whether preOccurence would be out of bounds of array, if not, compute,
			// whether preOccurence offers information about CompoundType
			if (preOccurence >= 0) {
				// iterate over all domain concepts
				for (String domainConcept : domainConcepts) {
					// if there is a match at index preoccurence and it does not equal
					// domainConceptToTest -> attributeType
					if (inputSplittedByWhitespace[preOccurence].equals(domainConcept)
							&& !inputSplittedByWhitespace[preOccurence].equals(domainConceptToTest)) {
						isAttributeType = true;
						logger.debug(domainConcept + "/AttributeType: " + isAttributeType);
						String preClass = inputSplittedByWhitespace[preOccurence];
						toReturn.add(new PossessionTuple(preClass, domainConceptToTest, Multiplicity.NO_INFO));
					}
				}
			}

			if (postOccurence <= maxIndex) {
				// iterate over all domain concepts
				for (String domainConcept : domainConcepts) {
					// if there is a match at index preoccurence and it does not equal
					// domainConceptToTest -> attributeType
					if (inputSplittedByWhitespace[postOccurence].equals(domainConcept)
							&& !inputSplittedByWhitespace[postOccurence].equals(domainConceptToTest)) {
						isClassType = true;
						logger.debug(domainConcept + "/ClassType: " + isClassType);
						String postAttribute = inputSplittedByWhitespace[postOccurence];
						toReturn.add(new PossessionTuple(domainConceptToTest, postAttribute, Multiplicity.NO_INFO));
					}
				}
			}
		}
		return toReturn;
	}

	public static List<PossessionTuple> computeCompoundTypeOfConcept(Set<String> allConcepts) {
		List<PossessionTuple> result = new ArrayList<>();

		for (String concept : allConcepts) {
			String[] splittedConcept = StringUtils.split(concept, "_");
			String[] splittedAndRegularized = SentenceRegularizerService.normalizeStringArray(splittedConcept);

			// check all every word for pre and post word, if both are domain concepts,
			// there's a possession relationship
			for (int i = 0; i < splittedAndRegularized.length; i++) {
				String currentWord = splittedAndRegularized[i];
				int postIndex = i + 1;
				if (allConcepts.contains(currentWord) && postIndex < splittedAndRegularized.length) {
					String followingWord = splittedAndRegularized[postIndex];
					if (allConcepts.contains(followingWord)) {
						result.add(new PossessionTuple(currentWord, followingWord, Multiplicity.NO_INFO));
					}
				}
			}
		}

		return result;
	}
}