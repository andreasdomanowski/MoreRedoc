package moreredoc.application;

import java.io.File;
import java.io.IOException;
import java.util.List;

import moreredoc.analysis.MoreRedocAnalysisConfiguration;
import moreredoc.application.exceptions.InvalidRequirementInputException;
import moreredoc.datainput.InputDataHandler;
import org.apache.log4j.Logger;

import moreredoc.analysis.MoreRedocAnalysis;
import moreredoc.datainput.SoftRedocDataHandler;
import moreredoc.project.data.MoreRedocProject;
import moreredoc.umlgenerator.ModelGenerator;
import moreredoc.utils.fileutils.CsvReader;

public class MoreRedocStarter {

    private MoreRedocStarter() {
    }

    private static Logger logger = Logger.getLogger(MoreRedocStarter.class);

    public static void generateModel(String pathCsvText, String pathOutputFolder, String pathCsvKeywords, MoreRedocOutputConfiguration outputConfiguration, MoreRedocAnalysisConfiguration analysisConfiguration) throws InvalidRequirementInputException, IOException {
        String csvDelimiter = ";";

        List<List<String>> keywordsRaw = CsvReader.readCsv(pathCsvKeywords, csvDelimiter);
        List<List<String>> sentencesRaw = CsvReader.readCsv(pathCsvText, csvDelimiter);

        InputDataHandler softRedocDataHandler = new SoftRedocDataHandler();

        MoreRedocProject project = new MoreRedocProject(keywordsRaw, sentencesRaw, softRedocDataHandler);

		logger.info("Start analysis");
		MoreRedocAnalysis analysis = new MoreRedocAnalysis(project, analysisConfiguration);
		logger.info("Analysis done.");

        String dslString = analysis.getModel().toPlantUmlDslString();
        logger.debug("PlantUML dsl string: " + dslString);

        logger.info("Start output file generation");
		ModelGenerator modGen = new ModelGenerator(dslString, outputConfiguration);
		modGen.generateModels(pathOutputFolder);
		logger.info("Output files generated");
	}

}
