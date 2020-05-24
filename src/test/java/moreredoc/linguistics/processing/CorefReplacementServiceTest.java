package moreredoc.linguistics.processing;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CorefReplacementServiceTest {
	
	String unresolvedSentence = "Mike drives a car. The car of him is green.";
	String unresolvedSentence2 = "Mike likes onions. He cooks regularly.";
	String unresolvedSentence3 = "Mike lives alone. His pet is named Waldi.";

	@Test
	public void test() {
		assertEquals("Mike drives a car . The car of Mike is green . ", CorefReplacementService.resolveCoreferences(unresolvedSentence));
		assertEquals("Mike likes onions . Mike cooks regularly . ", CorefReplacementService.resolveCoreferences(unresolvedSentence2));
		assertEquals("Mike lives alone . Mike pet is named Waldi . ", CorefReplacementService.resolveCoreferences(unresolvedSentence3));
	}

}
