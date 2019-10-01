package moreredoc.linguistics.processing;

import java.util.ArrayList;
import java.util.List;

public class MoreRedocStringUtils {
	/**
	 * Computes indices for occurences of a certain string in an array. Input has to
	 * be normalized.
	 * 
	 * @param inSplitted Splitted string in an array
	 * @param word       String whose occurences are calculated
	 * @return List containing all indices of occurences
	 */
	public static List<Integer> getIndicesForMatches(String[] inSplitted, String word) {
		List<Integer> toReturn = new ArrayList<>();

		// get indices for matches
		for (int i = 0; i < inSplitted.length; i++) {
			if (inSplitted[i].equals(word)) {
				toReturn.add(i);
			}
		}
		return toReturn;
	}
}
