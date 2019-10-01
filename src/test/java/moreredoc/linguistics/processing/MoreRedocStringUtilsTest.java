package moreredoc.linguistics.processing;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

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
}
