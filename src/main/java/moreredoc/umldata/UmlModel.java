package moreredoc.umldata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UmlModel {
	private static final String LINE_SEPARATOR = System.lineSeparator();

	private Map<String, UmlClass> classes;
	private List<UmlRelationship> relationships;
	
	public String toPlantUmlDslString() {
		StringBuilder plantTextBuilder = new StringBuilder("@startuml" + LINE_SEPARATOR);
		
		for(UmlClass c : classes.values()) {
			plantTextBuilder.append(c.toPlantUmlClassString()).append(LINE_SEPARATOR);
		}
		
		for(UmlRelationship r : relationships) {
			plantTextBuilder.append(r.toPlantUmlRelationshipString()).append(LINE_SEPARATOR);
		}
		plantTextBuilder.append("@enduml");
		
		return plantTextBuilder.toString();
	}

	public UmlModel(Map<String, UmlClass> classes, List<UmlRelationship> relationships) {
		super();
		this.classes = classes;
		this.relationships = relationships;
	}

	public List<UmlClass> getAllClassesImmutable() {
		return new ArrayList<>(classes.values());
	}

	public void setClasses(Map<String, UmlClass> classes) {
		this.classes = classes;
	}

	public List<UmlRelationship> getRelationships() {
		return this.relationships;
	}

	public void setRelationships(List<UmlRelationship> relationships) {
		this.relationships = relationships;
	}

	public Map<String, UmlClass> getNameToClassMapping(){
		return this.classes;
	}
	
	
}
