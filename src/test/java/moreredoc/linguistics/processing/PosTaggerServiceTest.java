package moreredoc.linguistics.processing;

import edu.stanford.nlp.ling.TaggedWord;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class PosTaggerServiceTest {
    @Test
    public void tagTest() {
        String s = "The customers' money was always in the manager's pockets.";
        List<TaggedWord> result = PosTaggerService.tag(s);
        
        assertEquals("DT", result.get(0).tag());
        assertEquals("NNS", result.get(1).tag());
        assertEquals("POS", result.get(2).tag());
        assertEquals("NN", result.get(3).tag());
        assertEquals("VBD", result.get(4).tag());
        assertEquals("RB", result.get(5).tag());
        assertEquals("IN", result.get(6).tag());
        assertEquals("DT", result.get(7).tag());
        assertEquals("NN", result.get(8).tag());
        assertEquals("POS", result.get(9).tag());
        assertEquals("NNS", result.get(10).tag());
        assertEquals(".", result.get(11).tag());
    }

}