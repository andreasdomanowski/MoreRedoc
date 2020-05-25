package moreredoc.linguistics.processing;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PosTaggerService{
    private PosTaggerService(){
    }

    public static List<TaggedWord> tag(String text) {
        String[] splitted = StringUtils.split(text);

        List<HasWord> wordList = new ArrayList<>();

        Arrays.stream(splitted).forEach( s -> wordList.add(new Word(s)));

        MaxentTagger maxentTagger = new MaxentTagger("edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger");
        return maxentTagger.tagSentence(wordList);
    }
}
