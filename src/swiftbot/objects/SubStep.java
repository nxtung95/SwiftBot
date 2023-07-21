package swiftbot.objects;

public class SubStep {
	private Coordinate start; // coordinate (x,y) for starting
	private Coordinate end; // coordinate (x,y) for ending


	public SubStep(Coordinate start, Coordinate end) {
		this.start = start;
		this.end = end;
	}

	public Coordinate getStart() {
		return start;
	}


	public Coordinate getEnd() {
		return end;
	}

}
