package moreredoc.datainput;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jboss.dna.common.text.Inflector;

import moreredoc.project.data.Requirement;

public final class SoftRedocDataHandler implements IInputDataProcessor {

	private static final String EXPRESSION_TEST = "TEST";
	private static final String EXPRESSION_DATA = "DATA";
	private static final String EXPRESSION_NEW_REQU = "REQU";
	private static final String EXPRESSION_NONFUNCT_REQU = "Non-Functional  requirements";
	private static final String EXPRESSION_BUSINESS_OBJECTS = "Business-Objects";
	private static final String PREFIX_FUNQ_REQ = "FUNC-REQ";

	private static final int INDEX_TYPE = 3;
	private static final int INDEX_TARGET_ENTITY = 4;

	private static final int INDEX_SENTENCE_REQU_ID = 0;
	private static final int INDEX_LN = 5;
	private static final int INDEX_SENTENCE = 6;
	private static final String lineNumberMetadata1 = "1";
	private static final String lineNumberMetadata2 = "2";
	

	@Override
	public List<Requirement> getRequirementsFromCsvInputs(List<List<String>> keywordInput,
			List<List<String>> sentencesInput) {
		return getRequirementSentences( getRequirementKeywords(keywordInput), sentencesInput);

	}
	
	private List<Requirement> getRequirementSentences(List<Requirement> requirements, List<List<String>> sentencesInput) {
		for(int i = 0; i < sentencesInput.size(); i++) {
			List<String> currentCsvLine = sentencesInput.get(i);
			String currentRequId = currentCsvLine.get(INDEX_SENTENCE_REQU_ID);
			String currentSentence = currentCsvLine.get(INDEX_SENTENCE);
			
			
			//iterate over requirements
			for(Requirement r : requirements) {
				//if requirements id in sentence csv matches one in the given requirements
				if(r.getId().trim().contains(currentRequId.trim())
						&& !currentCsvLine.get(INDEX_LN).contains(lineNumberMetadata1)
						&& !currentCsvLine.get(INDEX_LN).contains(lineNumberMetadata2)){
					r.concatenateUnprocessedText(currentSentence);
				}
			}
		}
		
		//delete every first two entries in sentences of requirements, because they just contain metadata, no sentences
//		for(Requirement r : requirements) {
//			//TODO
//			if(r.getUnprocessedText().size() > 2) {
//				r.getUnprocessedText().remove(0);
//				r.getUnprocessedText().remove(0);
//			}
//		}
		
		return requirements;
	}



	private List<Requirement> getRequirementKeywords(List<List<String>> keywordInput) {
		List<Requirement> result = new ArrayList<>();
		Set<String> currentKeywordSet = new HashSet<>();
		Requirement currentRequirement = new Requirement();
		boolean notSeenRequirement = true;

		// iterate over raw input, check every line in csv data
		for (int i = 0; i < keywordInput.size(); i++) {

			// current line in csv, represented as list of strings
			List<String> currentCsvLine = keywordInput.get(i);

			// if TYPE in csv equals "REQU", a new requirement begins
			if (currentCsvLine.get(INDEX_TYPE).equals(EXPRESSION_NEW_REQU)) {
				if(!notSeenRequirement) {
					currentRequirement.getKeywords().addAll(currentKeywordSet);
					result.add(currentRequirement);
					currentKeywordSet.clear();
				}
				notSeenRequirement = false;

				// reset current requirement
				currentRequirement = new Requirement();
				currentRequirement.setId(currentCsvLine.get(INDEX_TARGET_ENTITY));
			}


			// break, if nonfunctional requirements begin
			else if (currentCsvLine.get(INDEX_TARGET_ENTITY).equals(EXPRESSION_NONFUNCT_REQU)) {
				// add current requirement to result set to ensure, that the last requirement
				// will not be lost
				currentRequirement.getKeywords().addAll(currentKeywordSet);
				result.add(currentRequirement);
				break;
			}
			// if data is in current line, add it to current set, will be processed in next
			// iteration(s)
			else if (currentCsvLine.get(INDEX_TYPE).equals(EXPRESSION_DATA)) {
				//singularize domain concept from softRedoc
				String newKeyword = Inflector.getInstance().singularize(currentCsvLine.get(INDEX_TARGET_ENTITY));
				currentKeywordSet.add(newKeyword);
			}
		}

		return result;
	}

	public Set<String> getBusinessObjectsFromCsvInput(List<List<String>> csvInput) {
		Set<String> result = new HashSet<>();
		boolean inBusinessObjects = false;

		for (int i = 0; i < csvInput.size(); i++) {
			List<String> currentCsvLine = csvInput.get(i);
			if (!inBusinessObjects && currentCsvLine.get(INDEX_TARGET_ENTITY).equals(EXPRESSION_BUSINESS_OBJECTS)) {
				inBusinessObjects = true;
			} else {
				if (!currentCsvLine.get(INDEX_TYPE).equals(EXPRESSION_DATA)) {
					inBusinessObjects = false;
				}
			}

			if (inBusinessObjects && currentCsvLine.get(INDEX_TYPE).equals(EXPRESSION_DATA)) {
				result.add(currentCsvLine.get(INDEX_TARGET_ENTITY));
			}
		}
		return result;
	}
}
