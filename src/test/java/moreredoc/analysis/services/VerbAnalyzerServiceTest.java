package moreredoc.analysis.services;

import moreredoc.linguistics.processing.Commons;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VerbAnalyzerServiceTest {
    @Test
    public void testVerbCrop(){
        String sentence = "This should include me";
        String expected = "This include me";
        String result = VerbAnalyzerService.cropAuxiliaryAndNotModeledWords(sentence);

        assertEquals(expected,result);

        String singleForbiddenWord = Commons.VERBS_TO_NOT_MODEL.get(0);
        String expected2 = "";
        String result2 = VerbAnalyzerService.cropAuxiliaryAndNotModeledWords(singleForbiddenWord);

        assertEquals(expected2,result2);
    }
}