package moreredoc.datainput;

import moreredoc.application.exceptions.InvalidRequirementInputException;
import moreredoc.project.data.Requirement;

import java.util.List;
import java.util.Set;

public interface InputDataHandler {
	List<Requirement> getRequirementsFromCsvInputs(String keywordsPath,
			String textPath) throws InvalidRequirementInputException;

	/**
	 * Provides a possibility to add further domain concepts
	 * @param csvPath
	 */
	Set<String> getAdditionalDomainConcepts(String csvPath) throws InvalidRequirementInputException;

	String getCsvDelimiter();

	void configure();
}