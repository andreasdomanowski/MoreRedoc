package moreredoc.datainput;

import moreredoc.application.exceptions.InvalidRequirementInputException;
import moreredoc.project.data.Requirement;

import java.util.List;
import java.util.Set;

public interface InputDataHandler {
	List<Requirement> getRequirementsFromCsvInputs(List<List<String>> keywordInput,
			List<List<String>> sentencesInput) throws InvalidRequirementInputException;

	/**
	 * Provides a possibility to add further domain concepts
	 */
	Set<String> getAdditionalDomainConcepts(List<List<String>> csvInput) throws InvalidRequirementInputException;

	String getCsvDelimiter();

	void configure();
}
