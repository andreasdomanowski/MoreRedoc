package moreredoc.linguistics.processing;

import org.apache.commons.lang3.StringUtils;
import org.jboss.dna.common.text.Inflector;

public class WordRegularizerService {
    private WordRegularizerService() {

    }

    private static final Inflector INFLECTOR = new Inflector();

    /**
     * Regularizes a word. After regularization, the word is trimmed, in lower case and in singular.
     *
     * @param input Word which is to be regularized
     * @return Regularized word
     */
    public static String regularizeNoun(String input) {
        return INFLECTOR.singularize(input).toLowerCase().trim();
    }

    /**
     * Regularies every word in a given array of strings.
     *
     * @param sentence Sentence represented by string array
     * @return Regularized sentence in array
     */
    public static String[] arraySentenceRegularizer(String[] sentence) {
        for (int i = 0; i < sentence.length; i++) {
            sentence[i] = WordRegularizerService.regularizeNoun(sentence[i]);
        }
        return sentence;
    }

    /**
     * Removes genitive indicators form a given string and regularizes it. E.g. "Customer's" will be "customer"
     *
     * @param inputWord Word which is to be processed
     * @return Regularized word without genitive indicators
     */
    public static String scrapGenitive(String inputWord) {
        inputWord = inputWord.replace(Commons.GENITIVE_INDICATOR, "");
        return regularizeNoun(inputWord);
    }

    /**
     * Regularizes a verb. Therefore, auxiliary verbs are trimmed and partial stemming is performed.
     */
    public static String regularizeVerbPhrase(String in) {
        if (in == null) {
            return null;
        }

        String result = in.trim();

        // remove s at last char if present
        if (result.length() > 0 && result.charAt(result.length() - 1) == 's') {
            result =  result.substring(0, result.length() - 1);
        }

        // remove auxiliary verbs
        String[] splittedByWhitespace = StringUtils.split(result);

        StringBuilder resultBuilder = new StringBuilder();

        for(String s : splittedByWhitespace){
            if(!Commons.VERBS_TO_NOT_MODEL_WHEN_ALONE.contains(s)){
                resultBuilder.append(s).append(" ");
            }
        }

        return resultBuilder.toString().trim();
    }
}
