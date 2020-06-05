package moreredoc.linguistics;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.Properties;

public class MoreRedocNlpPipeline {
	private MoreRedocNlpPipeline(){

	}

	private static StanfordCoreNLP pipeline = null;

	public static synchronized StanfordCoreNLP getCoreNlpPipeline() {
		if (pipeline == null) {
			Properties props = new Properties();
			props.put("annotators", "tokenize, ssplit, pos, lemma, ner, depparse, mention, coref, natlog, openie");

			props.setProperty("openie.resolve_coref", "true");
			props.setProperty("openie.format", "default");
			props.setProperty("openie.max_entailments_per_clause", "90000");
			props.setProperty("openie.triple.all_nominals", "false");

			pipeline = new StanfordCoreNLP(props);
		}
		return pipeline;
	}
}
