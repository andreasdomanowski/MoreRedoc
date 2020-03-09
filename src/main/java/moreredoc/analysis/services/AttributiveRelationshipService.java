package moreredoc.analysis.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import moreredoc.analysis.data.PossessionTuple;
import moreredoc.linguistics.processing.MoreRedocStringUtils;
import moreredoc.linguistics.processing.WordRegularizerService;
import moreredoc.umldata.Multiplicity;

public class AttributiveRelationshipService {
	/**
	 * Service class, hide constructor
	 */
	private AttributiveRelationshipService() {

	}

	public static List<PossessionTuple> computeRelationshipTuples(String inputText, Set<String> domainConcepts) {
		String[] regularizedInputSplittedByWhitespace = StringUtils.split(inputText.trim());

		List<PossessionTuple> toReturn = new ArrayList<>();

		addPossessivesWithOf(regularizedInputSplittedByWhitespace, toReturn, domainConcepts);
		addPossessivesWithGenitive(regularizedInputSplittedByWhitespace, toReturn, domainConcepts);

		return toReturn;
	}

	private static void addPossessivesWithOf(String[] splittedInput, List<PossessionTuple> toReturn,
			Set<String> domainConcepts) {

		// find matches with "of" indicating a possessive case, compute their indices
		List<Integer> ofMatches = MoreRedocStringUtils.getIndicesForMatches(splittedInput, "of");

		for (int i : ofMatches) {
			int preOccurence = i - 1;
			int postOccurence = i + 1;

			// break, if word before or after "of" is out of bounds of array - then no
			// analysis can be done, both words have to be present
			if (preOccurence < 0 || postOccurence >= splittedInput.length) {
				break;
			}

			String preWord = splittedInput[preOccurence];
			String postWord = splittedInput[postOccurence];

			String preWordRegularized = WordRegularizerService.regularize(preWord);
			String postWordRegularized = WordRegularizerService.regularize(postWord);

			// if pre and post words are domain concepts, add this relationship to list
			if (domainConcepts.contains(preWordRegularized) && domainConcepts.contains(postWordRegularized)) {
				// initialize new attributive relationship
				// calculate multiplicity
				// TODO: implement multiplicity
				toReturn.add(new PossessionTuple(postWordRegularized, preWordRegularized, Multiplicity.NO_INFO));
				// toReturn.add(arg0)
			}
		}

	}

	private static void addPossessivesWithGenitive(String[] splittedInput, List<PossessionTuple> toReturn,
			Set<String> domainConcepts) {
		// TODO: implement multiplicity
		// vorgehen: suche domainconcept+'s oder +s'
		// bei matches prüfen, ob folgendes wort domain concept ist, oder nicht
		String genitiveIndicator = "'";

		// iterate over splitted Input, find words containing genitive indicator " ' "
		for (int i = 0; i < splittedInput.length; i++) {
			// if genitive indicactor found, check if current word (scraped and regularized)
			// is a domain concept and if there is another word in array
			if (splittedInput[i].contains(genitiveIndicator)
					&& domainConcepts.contains(WordRegularizerService.scrapGenitive(splittedInput[i]))
					&& (i + 1) < splittedInput.length) {
				String followingWordRegularized = WordRegularizerService.regularize(splittedInput[i + 1]);

				// if following word is a domnain concept, there's a possessive relationship
				if (domainConcepts.contains(followingWordRegularized)) {
					// delete genitive indicator from string, then regularize word
					String ownerConcept = WordRegularizerService
							.regularize(splittedInput[i].replace(genitiveIndicator, ""));
					if (domainConcepts.contains(ownerConcept)) {
						// add to list
						toReturn.add(new PossessionTuple(ownerConcept, followingWordRegularized, Multiplicity.NO_INFO));
					}
				}
			}
		}

	}
}
