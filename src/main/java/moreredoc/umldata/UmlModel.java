package moreredoc.umldata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UmlModel {
	private Map<String, UmlClass> classes;
	//private List<UmlClass> classes;
	private List<UmlRelationship> relationships;
	
	public String toPlantUmlDslString() {
		String toReturn = "@startuml\n";
		
		for(UmlClass c : classes.values()) {
			toReturn = toReturn + c.toPlantUmlClassString() + "\n";
		}
		
		for(UmlRelationship r : relationships) {
			toReturn = toReturn + r.toPlantUmlRelationshipString() + "\n";
		}
		
		return toReturn + "@enduml";
	}

	public UmlModel(Map<String, UmlClass> classes, List<UmlRelationship> relationships) {
		super();
		this.classes = classes;
		this.relationships = relationships;
	}

	public List<UmlClass> getAllClasses() {
		return new ArrayList<>(classes.values());
	}

	public void setClasses(Map<String, UmlClass> classes) {
		this.classes = classes;
	}

	public List<UmlRelationship> getRelationships() {
		return relationships;
	}

	public void setRelationships(List<UmlRelationship> relationships) {
		this.relationships = relationships;
	}
	
	
}
