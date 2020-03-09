package moreredoc.linguistics.processing;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class SentenceRegularizerServiceTest {

	@Test
	public void testArrayRegularizer() {
		String[] in = {"The", "customers", "share", "rooms"};
		String[] correctResult = {"the", "customer", "share","room"};
		
		assertArrayEquals(correctResult, SentenceRegularizerService.normalizeStringArray(in));
	}

}
