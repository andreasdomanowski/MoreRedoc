package moreredoc.analysis.services;

import moreredoc.analysis.data.PossessionTuple;
import moreredoc.linguistics.processing.Commons;
import moreredoc.linguistics.processing.MoreRedocStringUtils;
import moreredoc.linguistics.processing.SentenceRegularizerService;
import moreredoc.linguistics.processing.WordRegularizerService;
import moreredoc.umldata.Multiplicity;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CompoundAnalysisService {
    private static final Logger logger = Logger.getLogger(CompoundAnalysisService.class);

    private CompoundAnalysisService() {
    }

    public static Set<PossessionTuple> computeCompoundConceptsFromText(String in, String domainConceptToTest,
            Set<String> domainConcepts) {
        Set<PossessionTuple> toReturn = new HashSet<>();
        // domain concept can be attribute or/AND class type, initialize flags for this
        // combinations
        @SuppressWarnings("unused")
        boolean isAttributeType = false;
        @SuppressWarnings("unused")
        boolean isClassType = false;

        // regularized domain concept to test
        domainConceptToTest = WordRegularizerService.regularizeNoun(domainConceptToTest);

        // split input string by whitespace, read into array
        String[] inputSplittedByWhitespace = StringUtils.split(in.trim());

        // normalize every word in input string/array
        for (int i = 0; i < inputSplittedByWhitespace.length; i++) {
            inputSplittedByWhitespace[i] = WordRegularizerService.regularizeNoun(inputSplittedByWhitespace[i]);
        }

        // list containing every index of a occurrence in the array
        List<Integer> occurrenceIndices = MoreRedocStringUtils.getIndicesForMatches(inputSplittedByWhitespace,
                domainConceptToTest);

        int maxIndex = inputSplittedByWhitespace.length - 1;

        // iterate once again, check neighbourhood of occurrences
        for (int occurrenceIndex : occurrenceIndices) {
            // compute indices of words in input before and after occurence of domain
            // concept
            int preOccurrence = occurrenceIndex - 1;
            int postOccurrence = occurrenceIndex + 1;

            // check, whether preOccurence would be out of bounds of array, if not, compute,
            // whether preOccurence offers information about CompoundType
            if (preOccurrence >= 0) {
                // iterate over all domain concepts
                for (String domainConcept : domainConcepts) {
                    // if there is a match at index preoccurence and it does not equal
                    // domainConceptToTest -> attributeType
                    if (inputSplittedByWhitespace[preOccurrence].equals(domainConcept)
                            && !inputSplittedByWhitespace[preOccurrence].equals(domainConceptToTest)) {
                        isAttributeType = true;
                        logger.debug(domainConcept + "/AttributeType: " + isAttributeType);
                        String preClass = inputSplittedByWhitespace[preOccurrence];
                        toReturn.add(new PossessionTuple(preClass, domainConceptToTest, Multiplicity.NO_INFO));
                    }
                }
            }

            if (postOccurrence <= maxIndex) {
                // iterate over all domain concepts
                for (String domainConcept : domainConcepts) {
                    // if there is a match at index preoccurence and it does not equal
                    // domainConceptToTest -> attributeType
                    if (inputSplittedByWhitespace[postOccurrence].equals(domainConcept)
                            && !inputSplittedByWhitespace[postOccurrence].equals(domainConceptToTest)) {
                        isClassType = true;
                        logger.debug(domainConcept + "/ClassType: " + isClassType);
                        String postAttribute = inputSplittedByWhitespace[postOccurrence];
                        toReturn.add(new PossessionTuple(domainConceptToTest, postAttribute, Multiplicity.NO_INFO));
                    }
                }
            }
        }
        return toReturn;
    }

    /**
     * Analyzes all domain concepts and checks for the existence of compound concepts.
     */
    public static Set<PossessionTuple> computeCompoundConceptsFromAllConcepts(Set<String> allConcepts) {
        Set<PossessionTuple> result = new HashSet<>();

        for (String concept : allConcepts) {
            String[] splittedConcept = StringUtils.split(concept, Commons.COMPOUND_SEPARATOR);
            String[] splittedAndRegularized = SentenceRegularizerService.normalizeStringArray(splittedConcept);

            // check every word's successor, if both are domain concepts,
            // there's a possession relationship between the word and it's successor
			Set<CombinationTuple> possibleCombinations = getAllCombinationsForCompoundConcept(splittedAndRegularized);
			possibleCombinations.forEach(c -> {
				if(allConcepts.contains(c.first) && allConcepts.contains(c.second)){
					result.add(new PossessionTuple(c.first, c.second, Multiplicity.NO_INFO));
				}
			});

		}

        return result;
    }

	/**
	 * Computes all combinations for a compound concept.
	 * abcd -> a bcd; ab cd; abc d; b cd; bc d, c d
	 */
	private static Set<CombinationTuple> getAllCombinationsForCompoundConcept(String[] normalizedConceptSplitted) {
        Set<CombinationTuple> result = new HashSet<>();

        StringBuilder mergedNormalizedConceptBuilder = new StringBuilder();

        for (int i = 0; i < normalizedConceptSplitted.length; i++) {
            mergedNormalizedConceptBuilder.append(normalizedConceptSplitted[i]);
            if (i != normalizedConceptSplitted.length - 1) {
                mergedNormalizedConceptBuilder.append(Commons.COMPOUND_SEPARATOR);
            }
        }

        String reconstructedNormalizedConcept = mergedNormalizedConceptBuilder.toString();

        List<Integer> splitPositions = new ArrayList<>();

        int i = reconstructedNormalizedConcept.indexOf(Commons.COMPOUND_SEPARATOR);
        if (i != -1) {
            splitPositions.add(i);
        }
        while (i >= 0) {
            i = reconstructedNormalizedConcept.indexOf(Commons.COMPOUND_SEPARATOR, i + 1);
            if(i != -1){
                splitPositions.add(i);
            }
        }

        splitPositions.forEach(pos -> {
            String prefix = reconstructedNormalizedConcept.substring(0, pos);
            String suffix = reconstructedNormalizedConcept.substring(pos+1);
            result.add(new CombinationTuple(prefix, suffix));
        });
        return result;
    }

    private static class CombinationTuple {
        private final String first;
        private final String second;

        private CombinationTuple(String first, String second) {
            this.first = first;
            this.second = second;
        }
    }
}