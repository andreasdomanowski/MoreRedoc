package moreredoc.umldata;

public class UmlRelationship {
	private UmlClass from; 
	private UmlClass to;
	private UmlRelationshipType type;
	
	private String name;
	
	private static String relationshipSignAssociation = " -- ";
	private static String relationshipSignDirectedAssociation = " --> ";
	private static String relationshipSignAggregation = " *-- "; // from aggregates to
	private static String relationshipSignDependency = " ..|> "; 
	
	public String toPlantUmlRelationshipString() {
		String toReturn = from.getName();
		
		String relationshipToAdd = "";
		if(type == UmlRelationshipType.AGGREGATION) relationshipToAdd = relationshipSignAggregation;
		if(type == UmlRelationshipType.ASSOCIATION) relationshipToAdd = relationshipSignAssociation;
		if(type == UmlRelationshipType.DEPENDENCY) relationshipToAdd = relationshipSignDependency;
		if(type == UmlRelationshipType.DIRECTEDASSOCIATION) relationshipToAdd = relationshipSignDirectedAssociation;
		
		StringBuilder returnStringBuilder = new StringBuilder(toReturn + relationshipToAdd + to.getName());
		
		if(name != null) {
			returnStringBuilder.append(" : "+ name);
		}
		return returnStringBuilder.toString();
	}

	public UmlRelationship(UmlClass from, UmlClass to, UmlRelationshipType type, String name) {
		super();
		this.from = from;
		this.to = to;
		this.type = type;
		this.name = name;
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
	
	
}
