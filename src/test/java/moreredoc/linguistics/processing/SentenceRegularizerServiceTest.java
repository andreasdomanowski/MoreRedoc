package moreredoc.linguistics.processing;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SentenceRegularizerServiceTest {

	@Test
	public void arrayRegularizerTest() {
		String[] in = {"The", "customers", "share", "rooms"};
		String[] correctResult = {"the", "customer", "share","room"};
		
		assertArrayEquals(correctResult, SentenceRegularizerService.normalizeStringArray(in));
	}

	@Test
	public void removeStopwordsTest(){
		String stopwordSentenceThe = "The customers share the rooms";
		String stopwordSentenceA = "A should a another a ";
		String stopwordSentenceAn = "An xyz another an anonymous";

		assertEquals(" customers share rooms", SentenceRegularizerService.removeStopwords(stopwordSentenceThe));
		assertEquals(" should another ", SentenceRegularizerService.removeStopwords(stopwordSentenceA));
		assertEquals(" xyz another anonymous", SentenceRegularizerService.removeStopwords(stopwordSentenceAn));
	}

	@Test
	public void removeKeywordIndicatorsTest() {
		String sentenceWithStopwords = "Hello <name>. I am <years> old.";
		String sentenceWithoutStopwords = SentenceRegularizerService.removeKeywordIndicators(sentenceWithStopwords);
		assertEquals("Hello name. I am years old.", sentenceWithoutStopwords);
	}

}
