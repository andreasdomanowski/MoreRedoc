package moreredoc.linguistics;

import java.io.IOException;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class PosTagger {
    public static void tag(String text) throws IOException, ClassNotFoundException {

        MaxentTagger maxentTagger = new MaxentTagger("edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger");
        String tag = maxentTagger.tagString(text);
        String[] eachTag = tag.split("\\s+");
        System.out.println("Word\t\t" + "Standford tag");
        System.out.println("----------------------------------");
        for(int i = 0; i< eachTag.length; i++) {
            System.out.println(eachTag[i].split("_")[0] +"\t\t"+ eachTag[i].split("_")[1]);
        }

    }
}
