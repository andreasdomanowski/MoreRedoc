package moreredoc.analysis.services;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.plaf.ListUI;

import org.junit.Before;
import org.junit.Test;

import moreredoc.analysis.data.PossessionTuple;
import moreredoc.umldata.Multiplicity;

public class AttributiveRelationshipServiceTest {
	private String genitiveOwnershipSentenceSingular = "customer's id";
	private String genitiveOwnershipSentencePlural = "customers' id";
	private String ofOwnershipSentenceSingular = "id of customer";
	private String ofOwnershipSentencePlural = "ids of customer";
	private Set<String> domainConcepts = new HashSet<>();
	private PossessionTuple possession = new PossessionTuple("customer", "id", Multiplicity.NO_INFO);

	@Before
	public void setUp() throws Exception {
		domainConcepts.add("customer");
		domainConcepts.add("id");
	}

	@Test
	public void testGenitiveOwnershipSingular() {
		List<PossessionTuple> calculated = AttributiveRelationshipService
				.computeRelationshipTuples(genitiveOwnershipSentenceSingular, domainConcepts);
		
		
		if(calculated.isEmpty()) {
			fail("calculated list should not be empty");
		}
		
		for(PossessionTuple tuple : calculated) {
			assertEquals(possession, tuple);
		}
	}
	


}
