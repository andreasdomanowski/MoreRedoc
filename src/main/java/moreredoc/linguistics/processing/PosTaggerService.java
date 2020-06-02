package moreredoc.linguistics.processing;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.util.CoreMap;
import moreredoc.linguistics.MoreRedocNlpPipeline;

import java.util.ArrayList;
import java.util.List;

public class PosTaggerService {

    private PosTaggerService() {
    }

    public static List<TaggedWord> tag(String text) {
        List<TaggedWord> result = new ArrayList<>();

        Annotation annotation = new Annotation(text);
        MoreRedocNlpPipeline.getCoreNlpPipeline().annotate(annotation);

        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                String posTag = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                result.add(new TaggedWord(word, posTag));
            }
        }
        return result;
    }
}
