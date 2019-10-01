package moreredoc.analysis.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;

import edu.stanford.nlp.ie.util.RelationTriple;
import moreredoc.analysis.data.VerbCandidate;
import moreredoc.linguistics.processing.MoreRedocStringUtils;
import moreredoc.linguistics.processing.SentenceRegularizerService;
import moreredoc.linguistics.processing.WordRegularizerService;
import moreredoc.project.data.RelationTripleWrapper;

public class VerbAnalyzerService {
	public static Set<VerbCandidate> analyzeIETriples(List<RelationTripleWrapper> wrappedTriples, Set<String> domainConcepts) {
		Set<VerbCandidate> toReturn = new HashSet<>();
		
		List<VerbCandidate> oneOccurenceList = new ArrayList<>();
		List<VerbCandidate> twoOccurencesList = new ArrayList<>();

		// iterate over relation triples, compute, which are candidates for methods and for relationships
		// methods: domain concept in subject
		// relationship: domain concepts in subject and object
		for(RelationTripleWrapper currentWrapper : wrappedTriples) {
			boolean domainConceptInSubject = false;
			boolean domainConceptInObject = false;
			
			RelationTriple currentTriple = currentWrapper.getTriple();
			String[] subjectAsArray = StringUtils.split(currentTriple.subjectGloss().trim());
			String[] objectAsArray = StringUtils.split(currentTriple.objectGloss().trim());
			
			// normalize every word in input string/array
			String[] subjectAsArrayNormalized = SentenceRegularizerService.normalizeStringArray(subjectAsArray);
			String[] objecttAsArrayNormalized = SentenceRegularizerService.normalizeStringArray(objectAsArray);
			
			String subjectMatch = null;
			String objectMatch = null;
			
			for(int i = 0; i < subjectAsArrayNormalized.length; i++) {
				if(domainConcepts.contains(String.join(" ", subjectAsArrayNormalized[i]))) {
					domainConceptInSubject = true;
					subjectMatch = subjectAsArrayNormalized[i];
				}
			}
			
			for(int i = 0; i < objecttAsArrayNormalized.length; i++) {
				if(domainConcepts.contains(String.join(" ", objecttAsArrayNormalized[i]))) {
					domainConceptInObject= true;
					objectMatch = objecttAsArrayNormalized[i];
				}
			}
			
			if(domainConceptInSubject && !domainConceptInObject) {
				oneOccurenceList.add(new VerbCandidate(subjectMatch, null, currentTriple.relationGloss()));
			}
			
			if(domainConceptInSubject && domainConceptInObject) {
				twoOccurencesList.add(new VerbCandidate(subjectMatch, objectMatch, currentTriple.relationGloss()));
			}
			
		}
		
		// TODO: methoden
		oneOccurenceList.forEach(new Consumer<VerbCandidate>() {

			@Override
			public void accept(VerbCandidate t) {
				//System.out.println("one: "+t);
			}
			
		});
		twoOccurencesList.forEach(new Consumer<VerbCandidate>() {

			@Override
			public void accept(VerbCandidate t) {
				//System.out.println("two: "+t);
			}
			
		});
		
		toReturn.addAll(oneOccurenceList);
		toReturn.addAll(twoOccurencesList);
		
		return toReturn;
	}

	public Map<String, List<String>> getMethodsFromIETriples(List<RelationTripleWrapper> wrappedTriples,
			Set<String> domainConcepts) {
		return null;

	}

	public List<VerbCandidate> getRelationshipsFromIETriples(List<RelationTripleWrapper> wrappedTriples,
			Set<String> domainConcepts) {
		List<VerbCandidate> toReturn = null;
		return toReturn;
	}
	
	

}
