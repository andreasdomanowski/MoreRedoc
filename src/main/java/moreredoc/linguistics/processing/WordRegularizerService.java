package moreredoc.linguistics.processing;

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
    public static String regularize(String input) {
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
            sentence[i] = WordRegularizerService.regularize(sentence[i]);
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
        return regularize(inputWord);
    }
}
