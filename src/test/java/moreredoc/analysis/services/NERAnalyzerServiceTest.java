package moreredoc.analysis.services;

import moreredoc.analysis.data.NERType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NERAnalyzerServiceTest {
	
	private final String sentence = "My friends are in London this week. 10 is my favorite number. 100 dollar are much money.";

	@Test
	public void testNamedType() {
		String namedWord = "London";
		assertEquals(NERType.NAMED, NERAnalyzerService.getNERType(namedWord, sentence));
	}
	
	@Test
	public void testTimeType() {
		String timeWord = "week";
		assertEquals(NERType.TIME, NERAnalyzerService.getNERType(timeWord, sentence));
	}
	
	@Test
	public void testNumericalType() {
		String numericalWord = "dollar";
		assertEquals(NERType.NUMERICAL, NERAnalyzerService.getNERType(numericalWord, sentence));
	}
	
	@Test
	public void testNoneType() {
		String noneWord = "are";
		assertEquals(NERType.NONE, NERAnalyzerService.getNERType(noneWord, sentence));
	}

}
