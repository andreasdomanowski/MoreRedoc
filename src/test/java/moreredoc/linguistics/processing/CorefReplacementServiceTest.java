package moreredoc.linguistics.processing;

import org.junit.Test;

public class CorefReplacementServiceTest {
	
	String unresolvedSentence = "Mike drives a car. The car of him is green.";

	@Test
	public void test() {
		System.out.println(CorefReplacementService.resolveCoreferences(unresolvedSentence));
	}

}
