package moreredoc.datainput;

import moreredoc.application.exceptions.InvalidRequirementInputException;
import moreredoc.project.data.Requirement;

import java.util.List;

public interface InputDataHandler {
	List<Requirement> getRequirementsFromCsvInputs(List<List<String>> keywordInput,
			List<List<String>> sentencesInput) throws InvalidRequirementInputException;

	String getCsvDelimiter();

}
