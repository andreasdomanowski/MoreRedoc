package moreredoc.application;

import moreredoc.umldata.UmlClass;
import moreredoc.umldata.UmlModel;
import moreredoc.umldata.UmlRelationship;
import moreredoc.umldata.UmlRelationshipType;
import moreredoc.umlgenerator.ModelGenerator;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelGeneratorExample {
	private ModelGeneratorExample(){

	}

	public static void demonstrateModeling() throws IOException {
		List<String> methods = Arrays.asList("method1", "method2", "method3");

		List<String> class1Attributes = Arrays.asList("att1", "att2", "att3");
		List<String> class2Attributes = Arrays.asList("att1", "att2", "att3");
		List<String> class3Attributes = Arrays.asList("att1", "att2", "att3");

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
		ModelGenerator modGen = new ModelGenerator(dslString, new MoreRedocOutputConfiguration(true,true,true,true,true));
		modGen.generateModels(new File("").getAbsolutePath());
	}
}
