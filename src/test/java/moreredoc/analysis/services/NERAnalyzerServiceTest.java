package moreredoc.analysis.services;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import moreredoc.analysis.data.NERType;

public class NERAnalyzerServiceTest {
	
	private String sentence = "My friends are in London this week. 10 is my favorite number. 100 dollar are much money.";
	private String namedWord = "London";
	private String timeWord = "week";
	private String numericalWord = "dollar";
	private String noneWord = "are";

	@Test
	public void testNamedType() {
		assertEquals(NERType.NAMED, NERAnalyzerService.getNERType(namedWord, sentence));
	}
	
	@Test
	public void testTimeType() {
		assertEquals(NERType.TIME, NERAnalyzerService.getNERType(timeWord, sentence));
	}
	
	@Test
	public void testNumericalType() {
		assertEquals(NERType.NUMERICAL, NERAnalyzerService.getNERType(numericalWord, sentence));
	}
	
	@Test
	public void testNoneType() {
		assertEquals(NERType.NONE, NERAnalyzerService.getNERType(noneWord, sentence));
	}

}
