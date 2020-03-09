package moreredoc.application;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import moreredoc.umldata.UmlClass;
import moreredoc.umldata.UmlModel;
import moreredoc.umldata.UmlRelationship;
import moreredoc.umldata.UmlRelationshipType;
import moreredoc.umlgenerator.ModelGenerator;

public class ModelGeneratorExample {
	public static void demonstrateModeling() throws IOException {
		List<String> class1Attributes = Arrays.asList("att1", "att2", "att3");
		List<String> class1Methods= Arrays.asList("method1", "method2", "method3");
		
		List<String> class2Attributes = Arrays.asList("att1", "att2", "att3");
		List<String> class2Methods= Arrays.asList("method1", "method2", "method3");
		
		List<String> class3Attributes = Arrays.asList("att1", "att2", "att3");
		List<String> class3Methods = Arrays.asList("method1", "method2", "method3");
		
		UmlClass class1 = new UmlClass("Class1", class1Attributes, class1Methods);
		UmlClass class2 = new UmlClass("Class2", class2Attributes, class2Methods);
		UmlClass class3 = new UmlClass("Class3", class3Attributes, class3Methods);
		
		UmlRelationship rel1 = new UmlRelationship(class1, class2, UmlRelationshipType.AGGREGATION, null);
		UmlRelationship rel2 = new UmlRelationship(class2, class3, UmlRelationshipType.ASSOCIATION, null);
		UmlRelationship rel3 = new UmlRelationship(class3, class1, UmlRelationshipType.DIRECTEDASSOCIATION, null);
		UmlRelationship rel4 = new UmlRelationship(class3, class2, UmlRelationshipType.DEPENDENCY, null);
		
		List<UmlClass> classes = Arrays.asList(class1, class2, class3);
		List<UmlRelationship> relationships = Arrays.asList(rel1, rel2, rel3, rel4);
		
		Map<String, UmlClass> classMapping = new HashMap<>();
		classMapping.put("class1", class1);
		classMapping.put("class2", class2);
		classMapping.put("class3", class3);
		
		UmlModel exampleModel = new UmlModel(classMapping, relationships);
		
		ModelGenerator modGen = new ModelGenerator();
		String dslString = exampleModel.toPlantUmlDslString();
		
		modGen.drawPng(new File("model.png"), dslString) ;
		modGen.drawSvg(new File("model.svg"), dslString);
		modGen.generateRawXMI(new File("xmiRaw.xmi"), dslString);
		modGen.generateArgoXMI(new File("xmiArgo.xmi"), dslString);
		modGen.generateStarXMI(new File("xmiStar.xmi"), dslString);

	}
}
