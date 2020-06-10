package moreredoc.linguistics.processing;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class MoreRedocStringUtilsTest {

	@Test
	public void testGetIndicesForMatches() {
		String[] sentence = { "word", "customer", "word2", "customers", "customer", "word", "test" };
		String wordToFind = "customer";
		List<Integer> computedIndicesAsList = MoreRedocStringUtils.getIndicesForMatches(sentence, wordToFind);

		int[] computedIndicesAsArray = new int[computedIndicesAsList.size()];
		for(int i = 0; i < computedIndicesAsList.size(); i++) {
			computedIndicesAsArray[i] = computedIndicesAsList.get(i);
		}

		int[] correctIndicesAsArray = { 1, 4 };

		assertArrayEquals(correctIndicesAsArray, computedIndicesAsArray);
	}

	@Test
	public void testRemovePrefix(){
		String prefix = "customer";
		String toTest = "customer_number";
		String unchanged = "article_number";

		assertEquals("number", MoreRedocStringUtils.removePrefixConceptIfPresent(prefix, toTest));
		assertEquals(unchanged, MoreRedocStringUtils.removePrefixConceptIfPresent(prefix, unchanged));


	}
}
