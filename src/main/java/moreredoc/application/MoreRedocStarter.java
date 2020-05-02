package moreredoc.application;

import java.io.File;
import java.util.List;

import moreredoc.datainput.IInputDataProcessor;
import org.apache.log4j.Logger;

import moreredoc.analysis.MoreRedocAnalysis;
import moreredoc.datainput.SoftRedocDataHandler;
import moreredoc.project.data.MoreRedocProject;
import moreredoc.umlgenerator.ModelGenerator;
import moreredoc.utils.fileutils.CsvReader;

public class MoreRedocStarter {

	private MoreRedocStarter(){
	}
	
	private static Logger logger = Logger.getLogger(MoreRedocStarter.class);

	public static void generateModel(String pathCsvText, String pathOutputFolder, String pathCsvKeywords, MoreRedocOutputConfiguration outputConfiguration) throws Exception {
		String csvDelimiter = ";";
		
		List<List<String>> keywordsRaw = CsvReader.readCsv(pathCsvKeywords, csvDelimiter);
		List<List<String>> sentencesRaw = CsvReader.readCsv(pathCsvText, csvDelimiter);
	

		IInputDataProcessor softRedocDataHandler = new SoftRedocDataHandler();
		
		MoreRedocProject project = new MoreRedocProject(keywordsRaw, sentencesRaw, softRedocDataHandler);
		
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
