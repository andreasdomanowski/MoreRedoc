package moreredoc.linguistics.processing;

public class SentenceRegularizerService {
	private static String[] stopwords = {"The ", " the ", " an ", " a "};
	
	public static String regularizeSentence(String sentence) {
		String toReturn = removeKeywordIndicators(sentence);
		toReturn = removeStopwords(sentence);
		return toReturn;
	}
	
	public static String removeKeywordIndicators(String sentence) {
		return sentence.replace("<", "").replace(">", "");
	}
	
	public static String removeStopwords(String sentence) {
//		String toReturn = sentence;
//		
//		for(int i = 0; i < stopwords.length;i++	) {
//			toReturn = toReturn.replaceAll(stopwords[i], " ");
//		}
//		
//		return toReturn;
		return sentence.replaceAll("The ", "").replaceAll(" the ", " ").replaceAll(" an ", " ").replaceAll(" a ", " ");
	}
	
	public static String[] normalizeStringArray(String[] in) {
		String[] clonedArray = in.clone();
		for(int i = 0; i < in.length; i++) {
			clonedArray[i] = WordRegularizerService.regularize(in[i]);
		}
		
		
		return clonedArray;
	}
}
