package moreredoc.linguistics;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import org.apache.log4j.Logger;

public class PosTaggerService {
    private static final Logger logger = Logger.getLogger(PosTaggerService.class);


    private PosTaggerService(){

    }

    public static void tag(String text) {

        MaxentTagger maxentTagger = new MaxentTagger("edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger");
        String tag = maxentTagger.tagString(text);
        String[] eachTag = tag.split("\\s+");
        logger.info("Word\t\t" + "Standford tag");
        logger.info("----------------------------------");
        for(int i = 0; i< eachTag.length; i++) {
            logger.info(eachTag[i].split("_")[0] +"\t\t"+ eachTag[i].split("_")[1]);
        }

    }
}
