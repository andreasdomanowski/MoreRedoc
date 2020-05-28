package moreredoc.analysis.services;

import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.TaggedWord;
import moreredoc.analysis.data.VerbCandidate;
import moreredoc.linguistics.processing.Commons;
import moreredoc.linguistics.processing.PosTaggerService;
import moreredoc.linguistics.processing.SentenceRegularizerService;
import moreredoc.project.data.RelationTripleWrapper;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class VerbAnalyzerService {
    public static Set<VerbCandidate> analyzeIETriples(List<RelationTripleWrapper> wrappedTriples, Set<String> domainConcepts) {
        Set<VerbCandidate> toReturn = new HashSet<>();

        List<VerbCandidate> oneOccurrenceList = new ArrayList<>();
        List<VerbCandidate> twoOccurrencesList = new ArrayList<>();

        // iterate over relation triples, compute, which are candidates for methods and for relationships
        // methods: domain concept in subject
        // relationship: domain concepts in subject and object
        for (RelationTripleWrapper currentWrapper : wrappedTriples) {
            boolean domainConceptInSubject = false;
            boolean domainConceptInObject = false;

            RelationTriple currentTriple = currentWrapper.getTriple();
            String[] subjectAsArray = StringUtils.split(currentTriple.subjectGloss().trim());
            String[] objectAsArray = StringUtils.split(currentTriple.objectGloss().trim());

            // normalize every word in input string/array
            String[] subjectAsArrayNormalized = SentenceRegularizerService.normalizeStringArray(subjectAsArray);
            String[] objectAsArrayNormalized = SentenceRegularizerService.normalizeStringArray(objectAsArray);

            String subjectMatch = null;
            String objectMatch = null;

            for (int i = 0; i < subjectAsArrayNormalized.length; i++) {
                if (domainConcepts.contains(String.join(" ", subjectAsArrayNormalized[i]))) {
                    domainConceptInSubject = true;
                    subjectMatch = subjectAsArrayNormalized[i];
                }
            }

            for (int i = 0; i < objectAsArrayNormalized.length; i++) {
                if (domainConcepts.contains(String.join(" ", objectAsArrayNormalized[i]))) {
                    domainConceptInObject = true;
                    objectMatch = objectAsArrayNormalized[i];
                }
            }

            String verb = cropAuxiliaryAndNotModeledWords(currentTriple.relationGloss());
            if (!verb.isEmpty()) {
                if (domainConceptInSubject && !domainConceptInObject) {
                    oneOccurrenceList.add(new VerbCandidate(subjectMatch, null, verb));
                }

                if (domainConceptInSubject && domainConceptInObject) {
                    twoOccurrencesList.add(new VerbCandidate(subjectMatch, objectMatch, verb));
                }
            }
        }

        toReturn.addAll(oneOccurrenceList);
        toReturn.addAll(twoOccurrencesList);

        return toReturn;
    }

    public Map<String, List<String>> getMethodsFromIETriples(List<RelationTripleWrapper> wrappedTriples,
            Set<String> domainConcepts) {
        throw new UnsupportedOperationException();
    }

    public List<VerbCandidate> getRelationshipsFromIETriples(List<RelationTripleWrapper> wrappedTriples,
            Set<String> domainConcepts) {
        throw new UnsupportedOperationException();
    }

    public static String cropAuxiliaryAndNotModeledWords(String in) {
        List<TaggedWord> tags = PosTaggerService.tag(in);
        StringBuilder b = new StringBuilder();

        tags.forEach(x -> {
            if (!x.tag().equals(Commons.POS_MODAL))
                b.append(" ").append(x.word());
        });

        String result = b.toString().trim();

        if (Commons.VERBS_TO_NOT_MODEL_WHEN_ALONE.contains(result)) {
            return "";
        }
        return result;
    }

    /**
     * Crops VerbCandidates, whose verbs are a subset of another verb with the same "to" and "from"s
     */
    public static Set<VerbCandidate> cropPartialVerbs(Set<VerbCandidate> in) {
        return in.stream().filter(x -> {
            for (VerbCandidate c : in) {
                if (x.getTo().equals(c.getTo()) && x.getFrom().equals(c.getFrom()) && c.getVerb().contains(x.getVerb())) {
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toSet());
    }

}
