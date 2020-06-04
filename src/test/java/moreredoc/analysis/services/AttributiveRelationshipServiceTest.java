package moreredoc.analysis.services;

import moreredoc.analysis.data.PossessionTuple;
import moreredoc.umldata.Multiplicity;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AttributiveRelationshipServiceTest {
	private String genitiveOwnershipSentenceSingular = "customer's id";
	private String genitiveOwnershipSentencePlural = "customers' id";
	private String ofOwnershipSentenceSingular = "id of customer";
	private String ofOwnershipSentencePlural = "ids of customer";
	private Set<String> domainConcepts = new HashSet<>();
	private PossessionTuple possession = new PossessionTuple("customer", "id", Multiplicity.NO_INFO);

	@Before
	public void setUp() {
		domainConcepts.add("customer");
		domainConcepts.add("id");
	}

	@Test
	public void testGenitiveOwnershipSingular() {
		Set<PossessionTuple> calculated = AttributiveRelationshipService
				.computeRelationshipTuples(genitiveOwnershipSentenceSingular, domainConcepts);
		
		
		if(calculated.isEmpty()) {
			fail("calculated list should not be empty");
		}
		
		for(PossessionTuple tuple : calculated) {
			assertEquals(possession.getOwner(), tuple.getOwner());
			assertEquals(possession.getOwned(), tuple.getOwned());
		}
	}
	


}
