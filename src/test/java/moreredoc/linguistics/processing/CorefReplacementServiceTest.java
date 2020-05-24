package moreredoc.linguistics.processing;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CorefReplacementServiceTest {
	
	String unresolvedSentence = "Mike drives a car. The car of him is green.";

	@Test
	public void test() {
		assertEquals("Mike drives a car . The car of Mike is green . ", CorefReplacementService.resolveCoreferences(unresolvedSentence));
	}

}
