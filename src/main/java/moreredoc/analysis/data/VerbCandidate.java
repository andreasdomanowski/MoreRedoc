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
	
    //Idea from effective Java : Item 9
    @Override
    public int hashCode() {
    	String toComparator = "";
    	if(to != null) {
    		toComparator = this.to;
    	}
        int result = 17;
        result = 31 * result + from.hashCode();
        result = 31 * result + toComparator.hashCode();
        result = 31 * result + verb.hashCode();
        return result;
    }
    
    public boolean equals(Object o) {
    	if(o instanceof VerbCandidate) {
    		String toBuffer = "";
    		
    		VerbCandidate cand = (VerbCandidate) o;
    		
    		if(cand.getTo() != null) {
    			toBuffer = cand.getTo();
    		}
    		
    		return cand.getFrom().equals(this.from)
    				&& cand.getVerb().equals(this.verb)
    				&& toBuffer.equals(this.to);
    	}else {
    		return false;
    	}
    }

}
