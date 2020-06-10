package moreredoc.application;

import moreredoc.umldata.UmlClass;
import moreredoc.umldata.UmlModel;
import moreredoc.umldata.UmlRelationship;
import moreredoc.umldata.UmlRelationshipType;
import moreredoc.umlgenerator.PlantModelGenerator;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ModelGeneratorExample {
	private ModelGeneratorExample(){

	}

	public static void demonstrateModeling() throws IOException {
		Set<String> methods = new HashSet<>(Arrays.asList("method1", "method2", "method3"));

		Set<String> class1Attributes = new HashSet<>(Arrays.asList("att1", "att2", "att3"));
		Set<String> class2Attributes = new HashSet<>(Arrays.asList("att1", "att2", "att3"));
		Set<String> class3Attributes = new HashSet<>(Arrays.asList("att1", "att2", "att3"));

		UmlClass class1 = new UmlClass("Class1", class1Attributes, methods);
		UmlClass class2 = new UmlClass("Class2", class2Attributes, methods);
		UmlClass class3 = new UmlClass("Class3", class3Attributes, methods);
		
		UmlRelationship rel1 = new UmlRelationship(class1, class2, UmlRelationshipType.AGGREGATION, null, null);
		UmlRelationship rel2 = new UmlRelationship(class2, class3, UmlRelationshipType.ASSOCIATION, null, null);
		UmlRelationship rel3 = new UmlRelationship(class3, class1, UmlRelationshipType.DIRECTED_ASSOCIATION, null, null);
		UmlRelationship rel4 = new UmlRelationship(class3, class2, UmlRelationshipType.DEPENDENCY, null, null);
		
		List<UmlRelationship> relationships = Arrays.asList(rel1, rel2, rel3, rel4);
		
		Map<String, UmlClass> classMapping = new HashMap<>();
		classMapping.put("class1", class1);
		classMapping.put("class2", class2);
		classMapping.put("class3", class3);
		
		UmlModel exampleModel = new UmlModel(classMapping, relationships);
		
		String dslString = exampleModel.toPlantUmlDslString();
		MoreRedocOutputConfiguration outputConfiguration = new MoreRedocOutputConfiguration(new File("generatedModels").getAbsolutePath(), true, true, true, true, true, true);
		PlantModelGenerator modGen = new PlantModelGenerator(dslString, outputConfiguration);
		modGen.generateModels(outputConfiguration.getOutputFolder());
	}

	public static void main(String[] args) throws IOException {
		demonstrateModeling();
	}
}
