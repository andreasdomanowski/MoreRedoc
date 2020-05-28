package moreredoc.analysis;

public class MoreRedocAnalysisConfiguration {
	private final boolean modelVerbsAsRelationships;
	private final boolean modelVerbsAsMethods;
	private final boolean cropEmptyClasses;

	public MoreRedocAnalysisConfiguration(boolean modelVerbsAsMethods, boolean modelVerbsAsRelationships, boolean cropEmptyClasses) {
		this.modelVerbsAsMethods = modelVerbsAsMethods;
		this.modelVerbsAsRelationships = modelVerbsAsRelationships;
		this.cropEmptyClasses = cropEmptyClasses;
	}

	public boolean getModelVerbsAsMethods() {
		return modelVerbsAsMethods;
	}

	public boolean getModelVerbsAsRelationships() {
		return modelVerbsAsRelationships;
	}

	public boolean getCropEmptyClasses() {
		return cropEmptyClasses;
	}
}
