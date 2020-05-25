package moreredoc.linguistics.processing;

import edu.stanford.nlp.ling.TaggedWord;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class PosTaggerServiceTest {
    @Test
    public void tagTest() {
        String s = "The customer's money";
        List<TaggedWord> result = PosTaggerService.tag(s);

        assertEquals("DT", result.get(0).tag());
        assertEquals("NNS", result.get(1).tag());
        assertEquals("NN", result.get(2).tag());
    }

}