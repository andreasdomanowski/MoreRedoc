package moreredoc.datainput;

import java.util.List;

import moreredoc.application.exceptions.InvalidRequirementInputException;
import moreredoc.project.data.Requirement;

public interface IInputDataProcessor {
	public List<Requirement> getRequirementsFromCsvInputs(List<List<String>> keywordInput,
			List<List<String>> sentencesInput) throws InvalidRequirementInputException;

}
