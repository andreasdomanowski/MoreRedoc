package moreredoc.application;

import moreredoc.analysis.MoreRedocAnalysisConfiguration;
import moreredoc.analysis.MoreRedocModelGenerator;
import moreredoc.application.exceptions.InvalidRequirementInputException;
import moreredoc.datainput.InputDataHandler;
import moreredoc.project.data.MoreRedocProject;
import moreredoc.umlgenerator.PlantModelGenerator;
import org.apache.log4j.Logger;

import java.io.IOException;

public class MoreRedocStarter {

    private MoreRedocStarter() {
    }

    private static final Logger logger = Logger.getLogger(MoreRedocStarter.class);

    public static void generateModel(String pathCsvText, String pathCsvKeywords, MoreRedocOutputConfiguration outputConfiguration, MoreRedocAnalysisConfiguration analysisConfiguration, InputDataHandler inputDataHandler) throws InvalidRequirementInputException, IOException, InterruptedException {
        MoreRedocProject project = new MoreRedocProject(pathCsvKeywords, pathCsvText, inputDataHandler);

		logger.info("Start analysis");
		MoreRedocModelGenerator analysis = new MoreRedocModelGenerator(project, analysisConfiguration);
		logger.info("Analysis done.");

        String dslString = analysis.generateModel().toPlantUmlDslString();
        logger.debug("PlantUML dsl string: " + dslString);

        logger.info("Start output file generation");
		PlantModelGenerator modGen = new PlantModelGenerator(dslString, outputConfiguration);
		modGen.generateModels(outputConfiguration.getOutputFolder());
		logger.info("Output files generated");
	}

}
