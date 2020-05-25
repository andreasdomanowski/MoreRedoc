package moreredoc.analysis.services;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.simple.Sentence;
import moreredoc.analysis.data.NERType;
import moreredoc.linguistics.MoreRedocNlpPipeline;

import java.util.ArrayList;
import java.util.List;

public class NERAnalyzerService {
	private StanfordCoreNLP pipeline = MoreRedocNlpPipeline.getCoreNlpPipeline();
	
	// tags strings from https://stanfordnlp.github.io/CoreNLP/ner.html
	private static final String[] namedTypes = {"PERSON", "LOCATION", "ORGANIZATION", "MISC"};
	private static final String[] numericalTypes = {"MONEY", "NUMBER", "ORDINAL", "PERCENT" };
	private static final String[] timeTypes = {"DATE", "TIME", "DURATION", "SET" };
	
	public static NERType getNERType(String word, String context) {
		Sentence contextSentence = new Sentence(context);
		List<Integer> matchIndices = getOccurencesOfWord(word, contextSentence);
		
		//compute ner tags
		List<String> nerTags = contextSentence.nerTags(); 
		
		// check indices, if there is a match, 
		for(int i : matchIndices) {
			String currentNerTag = nerTags.get(i);
			if(doesWordExistInArray(currentNerTag, namedTypes)) return NERType.NAMED;
			if(doesWordExistInArray(currentNerTag, numericalTypes)) return NERType.NUMERICAL;
			if(doesWordExistInArray(currentNerTag, timeTypes)) return NERType.TIME;
		}
		
		return NERType.NONE;
	}
	
	private static List<Integer> getOccurencesOfWord(String inputWord, Sentence sentence){
		List<Integer> toReturn = new ArrayList<>();
		
		for(int i = 0; i < sentence.length(); i++) {
			if(sentence.word(i).equals(inputWord)) {
				toReturn.add(i);
			}
		}
		
		return toReturn;
	}
	
	private static boolean doesWordExistInArray(String word, String[] array) {
		for(int i = 0; i < array.length; i++) {
			if(word.equals(array[i])) {
				return true;
			}
		}
		
		return false;
	}
}
