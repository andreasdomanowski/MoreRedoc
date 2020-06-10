package moreredoc.linguistics.processing;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MoreRedocStringUtils {
	private MoreRedocStringUtils(){
	}

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

	public static String removePrefixConceptIfPresent(String prefix, String text){
		Objects.requireNonNull(prefix);
		Objects.requireNonNull(text);

		prefix = prefix + Commons.COMPOUND_SEPARATOR;

		if(text.startsWith(prefix) && text.split(prefix).length >= 2){
			return text.split(prefix)[1];
		}
		else {
			return text;
		}
	}
}
