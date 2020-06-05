package moreredoc.linguistics.processing;

import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import moreredoc.linguistics.MoreRedocNlpPipeline;
import moreredoc.project.data.RelationTripleWrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OpenIEService {
	/**
	 * Hide implicit constructor.
	 */
	private OpenIEService() {
		
	}

	public static OpenIEResult performIE(String text)  {
		List<RelationTripleWrapper> ieTriples = new ArrayList<>();

		StanfordCoreNLP pipeline = MoreRedocNlpPipeline.getCoreNlpPipeline();

		Annotation doc = new Annotation(text);

		pipeline.annotate(doc);
		List<String> sentencesWithoutOpenIeResult = new ArrayList<>();

		// Loop over sentences in the document
		for (CoreMap sentence : doc.get(CoreAnnotations.SentencesAnnotation.class)) {
			// compute collection of relation triples for current sentence
			Collection<RelationTriple> triples = sentence.get(NaturalLogicAnnotations.RelationTriplesAnnotation.class);

			for (RelationTriple triple : triples) {
				RelationTripleWrapper wrapper = new RelationTripleWrapper(sentence.toString(), triple);
				ieTriples.add(wrapper);
			}

			if (triples.isEmpty()) {
				sentencesWithoutOpenIeResult.add(sentence.toString());
			}

		}

		return new OpenIEResult(ieTriples, sentencesWithoutOpenIeResult);
	}

	public static class OpenIEResult{
		private final List<RelationTripleWrapper> ieResults;
		private final List<String> sentencesWithoutResults;

		public OpenIEResult(List<RelationTripleWrapper> ieResults, List<String> sentencesWithoutResults) {
			this.ieResults = ieResults;
			this.sentencesWithoutResults = sentencesWithoutResults;
		}

		public List<RelationTripleWrapper> getIeResults() {
			return ieResults;
		}

		public List<String> getSentencesWithoutResults() {
			return sentencesWithoutResults;
		}
	}

}
