package swiftbot.objects;

public class Step {
	private int time;
	private int direction; //0: Move backwards, 1: Move forward
	private String color;
	private Coordinate start; // coordinate (x,y) for starting
	private Coordinate end; // coordinate (x,y) for ending

	public Step() {
	}

	public Step(int time, int direction, String color, Coordinate start, Coordinate end) {
		this.time = time;
		this.direction = direction;
		this.color = color;
		this.start = start;
		this.end = end;
	}

	public String getColor() {
		return color;
	}

	public int getTime() {
		return time;
	}

	public int getDirection() {
		return direction;
	}

	public Coordinate getStart() {
		return start;
	}

	public void setStart(Coordinate start) {
		this.start = start;
	}

	public Coordinate getEnd() {
		return end;
	}

	public void setEnd(Coordinate end) {
		this.end = end;
	}
}
