package moreredoc.linguistics.processing;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

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

		assertEquals(WordRegularizerService.regularize(wordCustomer1), regularizedCustomer);
		assertEquals(WordRegularizerService.regularize(wordCustomer2), regularizedCustomer);
		assertEquals(WordRegularizerService.regularize(wordCustomer3), regularizedCustomer);
		assertEquals(WordRegularizerService.regularize(wordCustomer4), regularizedCustomer);
	}
	
	@Test
	public void testScrapGenitive() {
		String wordCustomer1 = "Customers";
		String wordCustomer2 = "Customers ";
		String wordCustomer3 = " Customers ";
		String wordCustomer4 = "customers";

		String scrapedAndRegularized = "customer";

		assertEquals(WordRegularizerService.regularize(wordCustomer1), scrapedAndRegularized);
		assertEquals(WordRegularizerService.regularize(wordCustomer2), scrapedAndRegularized);
		assertEquals(WordRegularizerService.regularize(wordCustomer3), scrapedAndRegularized);
		assertEquals(WordRegularizerService.regularize(wordCustomer4), scrapedAndRegularized);
	}
	
	

}
