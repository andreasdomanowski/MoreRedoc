package moreredoc.analysis.services;

import edu.stanford.nlp.ling.TaggedWord;
import moreredoc.analysis.data.PossessionTuple;
import moreredoc.linguistics.processing.Commons;
import moreredoc.linguistics.processing.MoreRedocStringUtils;
import moreredoc.linguistics.processing.PosTaggerService;
import moreredoc.linguistics.processing.WordRegularizerService;
import moreredoc.umldata.Multiplicity;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AttributiveRelationshipService {
    /**
     * Service class, hide constructor
     */
    private AttributiveRelationshipService() {

    }

    public static List<PossessionTuple> computeRelationshipTuples(String inputText, Set<String> domainConcepts) {
        String[] regularizedInputSplittedByWhitespace = StringUtils.split(inputText.trim());

        List<PossessionTuple> result = new ArrayList<>();

        result.addAll(addPossessivesWithOf(regularizedInputSplittedByWhitespace, domainConcepts));
		result.addAll(addPossessivesWithGenitive(regularizedInputSplittedByWhitespace, domainConcepts));

        return result;
    }

    private static List<PossessionTuple> addPossessivesWithOf(String[] splittedInput,
            Set<String> domainConcepts) {

        List<PossessionTuple> result = new ArrayList<>();

        // find matches with "of" indicating a possessive case, compute their indices
        List<Integer> ofMatches = MoreRedocStringUtils.getIndicesForMatches(splittedInput, "of");

        for (int i : ofMatches) {
            int preOccurrence = i - 1;
            int postOccurrence = i + 1;

            // break, if word before or after "of" is out of bounds of array - then no
            // analysis can be done, both words have to be present
            if (preOccurrence < 0 || postOccurrence >= splittedInput.length) {
                break;
            }

            String ownedRaw = splittedInput[preOccurrence];
            String ownerRaw = splittedInput[postOccurrence];

            Multiplicity multiplicity = computeMultiplicity(ownedRaw, ownerRaw);

			String ownerRegularized = WordRegularizerService.regularize(ownerRaw);
			String ownedRegularized = WordRegularizerService.regularize(ownedRaw);

            // if pre and post words are domain concepts, add this relationship to list
            if (domainConcepts.contains(ownedRegularized) && domainConcepts.contains(ownerRegularized)) {
                // initialize new attributive relationship
                // calculate multiplicity
                result.add(new PossessionTuple(ownerRegularized, ownedRegularized, multiplicity));
            }
        }

        return result;

    }

    private static List<PossessionTuple> addPossessivesWithGenitive(String[] splittedInput, Set<String> domainConcepts) {
        List<PossessionTuple> result = new ArrayList<>();
        // approach: search domainconcept+'s or +s'
        // iterate over splitted Input, find words containing genitive indicator " ' "
        for (int i = 0; i < splittedInput.length; i++) {
            // if genitive indicactor found, check if current word (scraped and regularized)
            // is a domain concept and if there is another word in array
            if (splittedInput[i].contains(Commons.GENITIVE_INDICATOR)
                    && domainConcepts.contains(WordRegularizerService.scrapGenitive(splittedInput[i]))
                    && (i + 1) < splittedInput.length) {

				String followingWordRaw = splittedInput[i + 1];
				String followingWordRegularized = WordRegularizerService.regularize(followingWordRaw);

                // if following word is a domnain concept, there's a possessive relationship
                if (domainConcepts.contains(followingWordRegularized)) {
					String ownerConceptRaw = splittedInput[i];
					// delete genitive indicator from string, then regularize word
					String ownerConceptRegularized = WordRegularizerService
                            .regularize(ownerConceptRaw.replace(Commons.GENITIVE_INDICATOR, ""));
                    if (domainConcepts.contains(ownerConceptRegularized)) {
                        // add to list
						Multiplicity multiplicity = computeMultiplicity(ownerConceptRaw, followingWordRaw);
                        result.add(new PossessionTuple(ownerConceptRegularized, followingWordRegularized, multiplicity));
                    }
                }
            }
        }
        return result;
    }

    private static Multiplicity computeMultiplicity(String genitiveOwner, String genitiveOwned) {
        List<TaggedWord> ownerPos = PosTaggerService.tag(genitiveOwner);
        List<TaggedWord> ownedPos = PosTaggerService.tag(genitiveOwned);

        Boolean ownerSingular = null;
        Boolean ownedSingular = null;

        if (ownerPos.size() != 1 || ownedPos.size() != 1) {
            return Multiplicity.NO_INFO;
        }

        String ownerPosTag = ownerPos.get(0).tag();
        String ownedPosTag = ownedPos.get(0).tag();

        if (ownerPosTag.equals(Commons.POS_NOUN_PROPER_SINGULAR) || ownerPosTag.equals(Commons.POS_NOUN_SINGULAR_OR_MASS)) {
            ownerSingular = Boolean.TRUE;
        }
        if (ownerPosTag.equals(Commons.POS_NOUN_PLURAL) || ownerPosTag.equals(Commons.POS_NOUN_PROPER_PLURAL)) {
            ownerSingular = Boolean.FALSE;
        }

        if (ownedPosTag.equals(Commons.POS_NOUN_PROPER_SINGULAR) || ownedPosTag.equals(Commons.POS_NOUN_SINGULAR_OR_MASS)) {
            ownedSingular = Boolean.TRUE;
        }
        if (ownedPosTag.equals(Commons.POS_NOUN_PLURAL) || ownedPosTag.equals(Commons.POS_NOUN_PROPER_PLURAL)) {
            ownedSingular = Boolean.FALSE;
        }

        if (ownerSingular == null || ownedSingular == null) {
            return Multiplicity.NO_INFO;
        }

        if (Boolean.TRUE.equals(ownerSingular) && Boolean.TRUE.equals(ownedSingular)) {
            return Multiplicity.ONE_TO_ONE;
        } else if (Boolean.TRUE.equals(ownerSingular)) {
            return Multiplicity.ONE_TO_MANY;
        } else if (Boolean.TRUE.equals(ownedSingular)) {
            return Multiplicity.MANY_TO_ONE;
        } else {
            return Multiplicity.MANY_TO_MANY;
        }

    }
}
