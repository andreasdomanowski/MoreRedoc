package moreredoc.analysis.services;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import moreredoc.analysis.data.CompoundType;

public class TextAnalysisServiceTest {

	String domainClass = "customer";
	String domainAttribute = "number";

	String[] domainConceptsArray = { "customer", "number" };
	Set<String> domainConcepts = new HashSet<String>(Arrays.asList(domainConceptsArray));

	@Test
	public void testCompoundTypeClass() {
		String classSentence = "customer enters his customer number";
		assertEquals(CompoundType.COMPOUND_CLASS,
				CompoundAnalysisService.computeCompoundType(classSentence, domainClass, domainConcepts));
	}
	
	@Test
	public void testCompoundTypeAttribute() {
		String classSentence = "customer enters his customer number";
		assertEquals(CompoundType.COMPOUND_ATTRIBUTE,
				CompoundAnalysisService.computeCompoundType(classSentence, domainAttribute, domainConcepts));
	}
	
	@Test
	public void testCompoundTypeBoth() {
		String classSentence = "customer number and number customer";
		assertEquals(CompoundType.COMPOUND_BOTH,
				CompoundAnalysisService.computeCompoundType(classSentence, domainAttribute, domainConcepts));
	}
	
	@Test
	public void testCompoundTypeNone() {
		String classSentence = "there is no customer who can enter a valid number";
		assertEquals(CompoundType.NONE,
				CompoundAnalysisService.computeCompoundType(classSentence, domainAttribute, domainConcepts));
	
	}

}
