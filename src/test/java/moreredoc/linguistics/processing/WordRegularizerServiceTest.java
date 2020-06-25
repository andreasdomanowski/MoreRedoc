package moreredoc.linguistics.processing;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class WordRegularizerServiceTest {

	@Test
	public void testSentenceRegularizer() {
		String[] sentence = { "The", "customers", "buy", "articles" };
		String[] regularizedSentence = { "the", "customer", "buy", "article" };
		String[] result = WordRegularizerService.arraySentenceRegularizer(sentence);

		assertArrayEquals(result, regularizedSentence);
	}

	@Test
	public void testWordRegularizer() {
		String wordCustomer1 = "Customers";
		String wordCustomer2 = "Customers ";
		String wordCustomer3 = " Customers ";
		String wordCustomer4 = "customers";

		String regularizedCustomer = "customer";

		assertEquals(WordRegularizerService.regularizeNoun(wordCustomer1), regularizedCustomer);
		assertEquals(WordRegularizerService.regularizeNoun(wordCustomer2), regularizedCustomer);
		assertEquals(WordRegularizerService.regularizeNoun(wordCustomer3), regularizedCustomer);
		assertEquals(WordRegularizerService.regularizeNoun(wordCustomer4), regularizedCustomer);
	}
	
	@Test
	public void testScrapGenitive() {
		String wordCustomer1 = "Customers";
		String wordCustomer2 = "Customers ";
		String wordCustomer3 = " Customers ";
		String wordCustomer4 = "customers";

		String scrapedAndRegularized = "customer";

		assertEquals(WordRegularizerService.regularizeNoun(wordCustomer1), scrapedAndRegularized);
		assertEquals(WordRegularizerService.regularizeNoun(wordCustomer2), scrapedAndRegularized);
		assertEquals(WordRegularizerService.regularizeNoun(wordCustomer3), scrapedAndRegularized);
		assertEquals(WordRegularizerService.regularizeNoun(wordCustomer4), scrapedAndRegularized);
	}
	
	

}
