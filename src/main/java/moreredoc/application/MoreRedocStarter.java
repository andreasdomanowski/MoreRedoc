package moreredoc.application;

import moreredoc.analysis.MoreRedocAnalysis;
import moreredoc.analysis.MoreRedocAnalysisConfiguration;
import moreredoc.application.exceptions.InvalidRequirementInputException;
import moreredoc.datainput.InputDataHandler;
import moreredoc.project.data.MoreRedocProject;
import moreredoc.umldata.UmlModel;
import moreredoc.umlgenerator.ModelOutputGenerator;
import org.apache.log4j.Logger;

import java.io.IOException;

public class MoreRedocStarter {

    private MoreRedocStarter() {
    }

    private static final Logger logger = Logger.getLogger(MoreRedocStarter.class);

    public static void generateModel(String pathCsvText, String pathCsvKeywords, MoreRedocOutputConfiguration outputConfiguration, MoreRedocAnalysisConfiguration analysisConfiguration, InputDataHandler inputDataHandler) throws InvalidRequirementInputException, IOException {
        MoreRedocProject project = new MoreRedocProject(pathCsvKeywords, pathCsvText, inputDataHandler);

		logger.info("Start analysis");
		MoreRedocAnalysis analysis = new MoreRedocAnalysis(project, analysisConfiguration);
		logger.info("Analysis done.");

        UmlModel model = analysis.generateModel();
        String dslString = model.toPlantUmlDslString();
        logger.debug("PlantUML dsl string: " + dslString);

        logger.info("Start output file generation");
		ModelOutputGenerator modGen = new ModelOutputGenerator(model, outputConfiguration);
		modGen.generateModels(outputConfiguration.getOutputFolder());
		logger.info("Output files generated");
	}

}
