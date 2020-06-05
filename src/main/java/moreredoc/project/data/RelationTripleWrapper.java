package moreredoc.project.data;

import edu.stanford.nlp.ie.util.RelationTriple;

public class RelationTripleWrapper {
    private final String sentence;
    private final RelationTriple triple;

    public RelationTripleWrapper(String sentence, RelationTriple triple) {
        super();
        this.sentence = sentence;
        this.triple = triple;
    }

    public String getSentence() {
        return sentence;
    }

    public RelationTriple getTriple() {
        return triple;
    }

    @Override
	public String toString() {
        return "RelationTripleWrapper{" +
                "sentence='" + sentence + '\'' +
                ", triple=" + triple +
                '}';
    }
}
