package moreredoc.analysis;

public class MoreRedocAnalysisConfiguration {
	private final boolean modelVerbsAsRelationships;
	private final boolean modelVerbsAsMethods;

	public MoreRedocAnalysisConfiguration(boolean modelVerbsAsMethods, boolean modelVerbsAsRelationships) {
		this.modelVerbsAsMethods = modelVerbsAsMethods;
		this.modelVerbsAsRelationships = modelVerbsAsRelationships;
	}

	public boolean getModelVerbsAsMethods() {
		return modelVerbsAsMethods;
	}

	public boolean getModelVerbsAsRelationships() {
		return modelVerbsAsRelationships;
	}
}
