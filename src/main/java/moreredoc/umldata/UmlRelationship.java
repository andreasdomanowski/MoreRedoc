package moreredoc.umldata;

public class UmlRelationship {
	private UmlClass from; 
	private UmlClass to;
	private UmlRelationshipType type;

	private Multiplicity multiplicity;
	
	private String name;

	private static final String RELATIONSHIP_SIGN_ASSOCIATION = " -- ";
	private static final String RELATIONSHIP_SIGN_DIRECTED_ASSOCIATION = " --> ";
	private static final String RELATIONSHIP_SIGN_AGGREGATION = " *-- "; // from aggregates to
	private static final String RELATIONSHIP_SIGN_DEPENDENCY = " ..|> ";
	
	public String toPlantUmlRelationshipString() {
		String toReturn = from.getName();
		
		String relationshipToAdd = "";
		if(type == UmlRelationshipType.AGGREGATION) relationshipToAdd = RELATIONSHIP_SIGN_AGGREGATION;
		if(type == UmlRelationshipType.ASSOCIATION) relationshipToAdd = RELATIONSHIP_SIGN_ASSOCIATION;
		if(type == UmlRelationshipType.DEPENDENCY) relationshipToAdd = RELATIONSHIP_SIGN_DEPENDENCY;
		if(type == UmlRelationshipType.DIRECTED_ASSOCIATION) relationshipToAdd = RELATIONSHIP_SIGN_DIRECTED_ASSOCIATION;
		
		StringBuilder returnStringBuilder = new StringBuilder(toReturn + relationshipToAdd + to.getName());
		
		if(name != null) {
			returnStringBuilder.append(" : "+ name);
		}
		return returnStringBuilder.toString();
	}

	public UmlRelationship(UmlClass from, UmlClass to, UmlRelationshipType type, String name, Multiplicity multiplicity) {
		super();
		this.from = from;
		this.to = to;
		this.type = type;
		this.name = name;
		this.multiplicity = multiplicity;
	}

	public UmlClass getFrom() {
		return from;
	}

	public void setFrom(UmlClass from) {
		this.from = from;
	}

	public UmlClass getTo() {
		return to;
	}

	public void setTo(UmlClass to) {
		this.to = to;
	}

	public UmlRelationshipType getType() {
		return type;
	}

	public void setType(UmlRelationshipType type) {
		this.type = type;
	}

	public Multiplicity getMultiplicity() {
		return multiplicity;
	}

	public void setMultiplicity(Multiplicity multiplicity) {
		this.multiplicity = multiplicity;
	}
	
	
}
