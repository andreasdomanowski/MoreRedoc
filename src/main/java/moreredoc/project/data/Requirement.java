package moreredoc.project.data;

import java.util.HashSet;
import java.util.Set;

public class Requirement {

	private String id;

	private Set<String> keywords = new HashSet<>();
	private String unprocessedText = "";

	public Requirement(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Set<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(Set<String> keywords) {
		this.keywords = keywords;
	}

	public String getUnprocessedText() {
		return unprocessedText;
	}
	
	public void concatenateUnprocessedText(String concat) {
		this.unprocessedText = this.unprocessedText + concat;
	}

	public void setUnprocessedText(String unprocessedText) {
		this.unprocessedText = unprocessedText;
	}


	public String toString() {
		return this.id + 
				"\n\t domain concepts: " + this.getKeywords()
				+ "\n\t unprocessed text: " + this.getUnprocessedText();
	}




}
