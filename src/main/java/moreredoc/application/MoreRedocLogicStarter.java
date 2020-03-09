package moreredoc.application;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import moreredoc.analysis.MoreRedocAnalysis;
import moreredoc.datainput.SoftRedocDataHandler;
import moreredoc.project.data.MoreRedocProject;
import moreredoc.project.data.Requirement;
import moreredoc.umlgenerator.ModelGenerator;
import moreredoc.utils.fileutils.CsvReader;

public class MoreRedocLogicStarter  {
	
	private static Logger logger = Logger.getLogger(MoreRedocLogicStarter.class);

	public static void runMoreRedocLogic(String pathCsvKeywords, String pathCsvText, String pathOutputFolder) throws Exception {
		
				
		//String filepathKeywords = "D:\\Cloud\\Dropbox\\Informatik\\Beleg\\_ORDERS.CSV";
		//String filepathText = "D:\\Cloud\\Dropbox\\Informatik\\Beleg\\_OrderEntry.CSV";
		
		String log4jConfPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
				+ File.separator + "resources" + File.separator + "logger" + File.separator + "log4j.properties";
		PropertyConfigurator.configure(log4jConfPath);

		String csvDelimiter = ";";
		
		List<List<String>> keywordsRaw = CsvReader.readCsv(pathCsvKeywords, csvDelimiter);
		List<List<String>> sentencesRaw = CsvReader.readCsv(pathCsvText, csvDelimiter);
	

		SoftRedocDataHandler softReadocDataHandler = new SoftRedocDataHandler();
		List<Requirement> requirements = softReadocDataHandler.getRequirementsFromCsvInputs(keywordsRaw, sentencesRaw);
		MoreRedocProject project = new MoreRedocProject(requirements);

		MoreRedocAnalysis analysis = new MoreRedocAnalysis(project, null);
		

//		System.out.println("Verb Analysis");
//		for (ProcessedRequirement r : project.getProcessedProjectRequirements()) {
////			System.out.println(r.toString());
////			r.printIE();
////			VerbAnalyzerService.analyzeIETriples(r.getRelationTriples(), project.getProjectDomainConcepts());
//		}

		// System.out.println(analysis.getModel().toPlantUmlDslString());
		
		ModelGenerator modGen = new ModelGenerator();
		String dslString = analysis.getModel().toPlantUmlDslString();
		
		modGen.drawPng(new File(pathOutputFolder + File.separator + "/model.png"), dslString) ;
		modGen.drawSvg(new File(pathOutputFolder + File.separator + "model.svg"), dslString);
		modGen.generateRawXMI(new File(pathOutputFolder + File.separator + "xmiRaw.xmi"), dslString);
		modGen.generateArgoXMI(new File(pathOutputFolder + File.separator + "xmiArgo.xmi"), dslString);
		modGen.generateStarXMI(new File(pathOutputFolder + File.separator + "xmiStar.xmi"), dslString);

	}


}
