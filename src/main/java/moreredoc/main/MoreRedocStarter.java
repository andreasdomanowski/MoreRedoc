package moreredoc.main;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.PropertyConfigurator;

import edu.stanford.nlp.ie.util.RelationTriple;
import moreredoc.analysis.MoreRedocAnalysis;
import moreredoc.analysis.data.CompoundType;
import moreredoc.analysis.data.PossessionTuple;
import moreredoc.analysis.services.AttributiveRelationshipService;
import moreredoc.analysis.services.CompoundAnalysisService;
import moreredoc.analysis.services.VerbAnalyzerService;
import moreredoc.datainput.SoftRedocDataHandler;
import moreredoc.project.data.MoreRedocProject;
import moreredoc.project.data.ProcessedRequirement;
import moreredoc.project.data.RelationTripleWrapper;
import moreredoc.project.data.Requirement;
import moreredoc.utils.fileutils.CsvReader;
import net.sourceforge.plantuml.project3.Verb;

public class MoreRedocStarter {

	public static void main(String[] args) throws Exception {
		String log4jConfPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
				+ File.separator + "resources" + File.separator + "logger" + File.separator + "log4j.properties";
		PropertyConfigurator.configure(log4jConfPath);

		CsvReader reader = new CsvReader();
		List<List<String>> keywordsRaw = reader.readCsv("D:\\Cloud\\Dropbox\\Informatik\\Beleg\\_ORDERS.CSV", ";");
		List<List<String>> sentencesRaw = reader.readCsv("D:\\Cloud\\Dropbox\\Informatik\\Beleg\\_OrderEntry.CSV", ";");

		SoftRedocDataHandler softReadocDataHandler = new SoftRedocDataHandler();
		List<Requirement> requirements = softReadocDataHandler.getRequirementsFromCsvInputs(keywordsRaw, sentencesRaw);
		MoreRedocProject project = new MoreRedocProject(requirements);

		MoreRedocAnalysis analysis = new MoreRedocAnalysis(project);

		System.out.println("Verb Analysis");
		for (ProcessedRequirement r : project.getProcessedProjectRequirements()) {
			System.out.println(r.toString());
			r.printIE();
			VerbAnalyzerService.analyzeIETriples(r.getRelationTriples(), project.getProjectDomainConcepts());
		}

		// System.out.println(analysis.getModel().toPlantUmlDslString());

	}

}
