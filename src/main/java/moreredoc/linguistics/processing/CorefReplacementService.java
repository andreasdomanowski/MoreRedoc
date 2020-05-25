package moreredoc.linguistics.processing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.stanford.nlp.coref.CorefCoreAnnotations;
import edu.stanford.nlp.coref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.coref.data.CorefChain.CorefMention;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import moreredoc.linguistics.MoreRedocNlpPipeline;

public class CorefReplacementService {
	
	/**
	 * Utility class, not meant to be instantiated, thus constructor is hidden
	 */
	private CorefReplacementService() {
		
	}
	
	public static String resolveCoreferences(String text) {
		StanfordCoreNLP pipeline = MoreRedocNlpPipeline.getCoreNlpPipeline();
		Annotation doc = new Annotation(text);
		pipeline.annotate(doc);

		Map<Integer, CorefChain> corefs = doc.get(CorefChainAnnotation.class);
		List<CoreMap> sentences = doc.get(CoreAnnotations.SentencesAnnotation.class);

		List<String> resolved = new ArrayList<>();

		for (CoreMap sentence : sentences) {

			List<CoreLabel> tokens = sentence.get(CoreAnnotations.TokensAnnotation.class);

			for (CoreLabel token : tokens) {

				Integer corefClustId = token.get(CorefCoreAnnotations.CorefClusterIdAnnotation.class);

				CorefChain chain = corefs.get(corefClustId);

				if (chain == null) {
					resolved.add(token.word());
				} else {

					int sentINdx = chain.getRepresentativeMention().sentNum - 1;
					CoreMap corefSentence = sentences.get(sentINdx);
					List<CoreLabel> corefSentenceTokens = corefSentence.get(TokensAnnotation.class);
					String newwords = "";
					CorefMention reprMent = chain.getRepresentativeMention();
					if (token.index() <= reprMent.startIndex || token.index() >= reprMent.endIndex) {
						for (int i = reprMent.startIndex; i < reprMent.endIndex; i++) {
							CoreLabel matchedLabel = corefSentenceTokens.get(i - 1);
							resolved.add(matchedLabel.word().replace("'s", ""));
							newwords += matchedLabel.word() + " ";

						}
					}

					else {
						resolved.add(token.word());
					}
				}

			}

		}

		StringBuilder stringBuilder = new StringBuilder();

		resolved.stream().forEach(str -> stringBuilder.append(str + " "));

		return stringBuilder.toString();

	}
}
