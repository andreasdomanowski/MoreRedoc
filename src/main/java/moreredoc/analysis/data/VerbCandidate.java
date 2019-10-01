package moreredoc.analysis.data;

import moreredoc.umldata.UmlRelationshipType;

public class VerbCandidate {
	private String from;
	private String to;
	private String verb;

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}
	
	public String getVerb() {
		return this.verb;
	}

	public VerbCandidate(String from, String to, String verb) {
		super();
		this.from = from;
		this.to = to;
		this.verb = verb;
	}
	
	public String toString() {
		return "F: " + from + " " + " V " + verb + " " + "T: " + to;
	}

}
