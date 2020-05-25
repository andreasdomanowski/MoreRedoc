package moreredoc.linguistics.processing;

public class SentenceRegularizerService {
	private SentenceRegularizerService(){

	}

	private static final String[] STOP_WORDS = {"The ", " the ", "An ", " an ","A ", " a "};
	
	public static String regularizeSentence(String sentence) {
		String toReturn = removeKeywordIndicators(sentence);
		toReturn = removeStopwords(toReturn);
		return toReturn;
	}
	
	public static String removeKeywordIndicators(String sentence) {
		return sentence.replace("<", "").replace(">", "");
	}
	
	public static String removeStopwords(String sentence) {
		String toReturn = sentence;
		for(int i = 0; i < STOP_WORDS.length; i++	) {
			toReturn = toReturn.replace(STOP_WORDS[i], " ");
		}
		return toReturn;
	}
	
	public static String[] normalizeStringArray(String[] in) {
		String[] clonedArray = in.clone();
		for(int i = 0; i < in.length; i++) {
			clonedArray[i] = WordRegularizerService.regularize(in[i]);
		}

		return clonedArray;
	}
}
