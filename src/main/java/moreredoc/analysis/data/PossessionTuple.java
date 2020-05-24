package moreredoc.analysis.data;

import moreredoc.umldata.Multiplicity;

import java.util.Objects;

public class PossessionTuple {
	private String owner;
	private String owned;
	private Multiplicity multiplicity;

	public PossessionTuple(String owner, String owned, Multiplicity multiplicity) {
		super();
		this.owner = owner;
		this.owned = owned;
		this.multiplicity = multiplicity;
	}

	public String getOwner() {
		return owner;
	}

	public String getOwned() {
		return owned;
	}

	public Multiplicity getMultiplicity() {
		return multiplicity;
	}

	public String toString() {
		return owner + " owns " + owned;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof PossessionTuple)) {
			return false;
		} else {
			PossessionTuple castedObject = (PossessionTuple) object;
			return this.owned.equals(castedObject.getOwned()) && this.owner.equals(castedObject.getOwner())
					&& this.multiplicity.equals(castedObject.getMultiplicity());
		}
	}

	@Override
	public int hashCode(){
		return Objects.hash(owner, owned, multiplicity);
	}

}
