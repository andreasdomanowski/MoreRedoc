package moreredoc.linguistics.processing;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import moreredoc.linguistics.MoreRedocNlpPipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PosTaggerService {
    //lightweight pipeline with just the needed annotations for pos tagging. Needed for CI environment
    private static StanfordCoreNLP posPipeline = null;

    private PosTaggerService() {
    }

    private static synchronized StanfordCoreNLP getPosPipeline() {
        // if property not defined, use default pipeline
        String line = System.getProperty(Commons.JVM_PROPERTY_USE_PROJECT_PIPELINE_FOR_POS_TAGGING);
        if(line == null){
            return MoreRedocNlpPipeline.getCoreNlpPipeline();
        }

        Boolean useProjectPipelineForPosTagging = Boolean.parseBoolean(line);
        if(Boolean.TRUE.equals(useProjectPipelineForPosTagging)){
            return MoreRedocNlpPipeline.getCoreNlpPipeline();
        }

        if (posPipeline == null) {
            Properties props = new Properties();
            props.setProperty("annotators", "tokenize, ssplit, pos");
            posPipeline = new StanfordCoreNLP(props);
        }

        return posPipeline;
    }

    public static List<TaggedWord> tag(String text)  {
        List<TaggedWord> result = new ArrayList<>();

        Annotation annotation = new Annotation(text);
        getPosPipeline().annotate(annotation);

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
