package moreredoc.linguistics.processing;

import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
import edu.stanford.nlp.util.CoreMap;
import moreredoc.linguistics.MoreRedocNlpPipeline;
import moreredoc.project.data.RelationTripleWrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * A demo illustrating how to call the OpenIE system programmatically.
 */
public class OpenIEService {

	public static List<RelationTripleWrapper> performIE(String text) throws Exception {
		List<RelationTripleWrapper> toReturn = new ArrayList<>();

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
				toReturn.add(wrapper);
			}

			if (triples.isEmpty()) {
				sentencesWithoutOpenIeResult.add(sentence.toString());
			} else {
				// System.out.println("analyzing: " + sentence);
				for (RelationTriple triple : triples) {
//					System.out.println(triple.confidence + "\t" + triple.subjectLemmaGloss() + "\t"
//							+ triple.relationLemmaGloss() + "\t" + triple.objectLemmaGloss());
				}
			}

		}

		// System.out.println("sentences without results:");
		// for (String s : sentencesWithoutOpenIeResult)
		// System.out.println("\t" + s);

		// System.out.println("\t analysis end\n\n");

		return toReturn;
	}

}
