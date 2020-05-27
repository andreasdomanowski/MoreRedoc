package moreredoc.application;

import moreredoc.analysis.MoreRedocAnalysisConfiguration;
import moreredoc.analysis.MoreRedocModelGenerator;
import moreredoc.application.exceptions.InvalidRequirementInputException;
import moreredoc.datainput.InputDataHandler;
import moreredoc.project.data.MoreRedocProject;
import moreredoc.umlgenerator.ModelGenerator;
import moreredoc.utils.fileutils.CsvReader;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class MoreRedocStarter {

    private MoreRedocStarter() {
    }

    private static Logger logger = Logger.getLogger(MoreRedocStarter.class);

    public static void generateModel(String pathCsvText, String pathCsvKeywords, MoreRedocOutputConfiguration outputConfiguration, MoreRedocAnalysisConfiguration analysisConfiguration, InputDataHandler inputDataHandler) throws InvalidRequirementInputException, IOException {
        List<List<String>> keywordsRaw = CsvReader.readCsv(pathCsvKeywords, inputDataHandler.getCsvDelimiter());
        List<List<String>> sentencesRaw = CsvReader.readCsv(pathCsvText, inputDataHandler.getCsvDelimiter());

        MoreRedocProject project = new MoreRedocProject(keywordsRaw, sentencesRaw, inputDataHandler);

		logger.info("Start analysis");
		MoreRedocModelGenerator analysis = new MoreRedocModelGenerator(project, analysisConfiguration);
		logger.info("Analysis done.");

        String dslString = analysis.generateModel().toPlantUmlDslString();
        logger.debug("PlantUML dsl string: " + dslString);

        logger.info("Start output file generation");
		ModelGenerator modGen = new ModelGenerator(dslString, outputConfiguration);
		modGen.generateModels(outputConfiguration.getOutputFolder());
		logger.info("Output files generated");
	}

}
