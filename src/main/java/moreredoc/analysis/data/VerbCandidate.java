package moreredoc.analysis.data;

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

	// Idea from effective Java : Item 9
	@Override
	public int hashCode() {
		String toComparator = "";
		if (to != null) {
			toComparator = this.to;
		}
		int result = 17;
		result = 31 * result + from.hashCode();
		result = 31 * result + toComparator.hashCode();
		result = 31 * result + verb.hashCode();
		return result;
	}

	public boolean equals(Object o) {
		if (o instanceof VerbCandidate) {
			VerbCandidate cand = (VerbCandidate) o;
			// first check "from" and "verb", because both are always set
			if (this.from.equals(cand.from) && this.verb.equals(cand.verb)) {
				// if both "to"s are not null, compare them
				if (this.to != null && cand.to != null) {
					return this.to.equals(cand.to);
				// if 
					// TODO
				} else {
					return (this.to == null && cand.to == null);
				}
			} else {
				return false;
			}

		}
		// if o is not instance of VerbCandidate, return false
		else {
			return false;
		}
	}

}
