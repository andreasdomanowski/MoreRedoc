package moreredoc.analysis.services;

import moreredoc.analysis.data.PossessionTuple;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class TextAnalysisServiceTest {

    String domainClass = "customer";
    String domainAttribute = "number";

    String[] domainConceptsArray = { "customer", "number" };
    Set<String> domainConcepts = new HashSet<>(Arrays.asList(domainConceptsArray));

    @Test
    public void testCompoundTypeClass() {
        String classSentence = "customer enters his customer number";
        List<PossessionTuple> result = CompoundAnalysisService.computePossessionTuples(classSentence, domainClass, domainConcepts);
        assertEquals(1, result.size());
        assertEquals(result.get(0).getOwner(), domainClass);
        assertEquals(result.get(0).getOwned(), domainAttribute);

    }

    @Test
    public void testCompoundTypeNone() {
        String classSentence = "there is no customer who can enter a valid number";
        assertEquals(0, CompoundAnalysisService.computePossessionTuples(classSentence, domainAttribute, domainConcepts).size());
    }

}
