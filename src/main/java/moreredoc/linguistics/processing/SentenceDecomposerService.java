package moreredoc.linguistics.processing;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import edu.stanford.nlp.ling.Label;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.trees.Tree;
import moreredoc.project.data.DecomposedSentenceTripel;

public class SentenceDecomposerService {

    /**
     * Utility class, not meant to be instantiated, thus constructor is hidden
     */
    private SentenceDecomposerService() {

    }

    public static List<DecomposedSentenceTripel> decomposeSBARSentences(String in) {
        List<DecomposedSentenceTripel> toReturn = new ArrayList<>();
        Document doc = new Document(in);

        for (int i = 0; i < doc.sentences().size(); i++) {
            DecomposedSentenceTripel currentTripel = new DecomposedSentenceTripel();

            List<Tree> sbarPartsOfSentences = new ArrayList<>();
            Tree depTree = doc.sentence(i).parse();

            depTree.forEach(arg0 -> {
                if (arg0.label().toString().equals("SBAR")) {
                    sbarPartsOfSentences.add(arg0);
                }
            });

            // whole sentence with all parts
            String originalSentenceReconstructed = reconstructSentenceFromTree(depTree);
            currentTripel.setOriginalSentence(originalSentenceReconstructed);

            for (Tree currentSubtree : sbarPartsOfSentences) {
                // new (sub)-sentence from SBAR-Parts of a sentence
                String newSentence = reconstructSentenceFromTree(currentSubtree);
                currentTripel.addSubentence(newSentence);

                String originalWithoutPartSentence = originalSentenceReconstructed.replace(removeLastChar(newSentence),
                        "");
                originalWithoutPartSentence = removeLeadingPunctuation(originalWithoutPartSentence);
                currentTripel.setOriginalWithoutPartSentences(originalWithoutPartSentence);
            }

            toReturn.add(currentTripel);
        }

        return toReturn;

    }

    /*
     * Reconstructs a sentence from the parse tree.
     */
    private static String reconstructSentenceFromTree(Tree t) {
        List<Label> list = t.yield();

        StringBuilder textBuilder = new StringBuilder();

        for (Label l : list) {
            textBuilder.append(l.toString() + " ");
        }

        return sanitizeSentenceFromTree(textBuilder.toString());
    }

    /**
     * Sanitizes sentences which are constructed from subtrees (removes leading commata and makes sure that the sentence ends with a dot)
     *
     * @param reconstructedSentence unsanitized reconstructed sentence from subtree
     * @return sanitized reconstructed sentence from subtree
     */
    private static String sanitizeSentenceFromTree(String reconstructedSentence) {
        reconstructedSentence = reconstructedSentence.trim();
        if (reconstructedSentence.startsWith(","))
            reconstructedSentence = reconstructedSentence.substring(1);
        if (!reconstructedSentence.endsWith("."))
            reconstructedSentence = reconstructedSentence.concat(".");

        return reconstructedSentence.trim();
    }

    /**
     * Helper function for removal of last char in a string
     */
    private static String removeLastChar(String inputString) {
        if (inputString != null && inputString.length() > 0)
            return inputString.substring(0, inputString.length() - 1);
        else
            return inputString;
    }

    /**
     * Helper Function for removing leading commata from a string
     */
    private static String removeLeadingPunctuation(String inputString) {
        String toReturn = inputString.trim();
        return toReturn.startsWith(",") ? toReturn.substring(1).trim() : toReturn;
    }
}