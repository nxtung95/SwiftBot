package swiftbot.objects;

import java.util.List;

public class Step {
	private int time;
	private int direction; //0: Move backwards, 1: Move forward
	private Coordinate start; // coordinate (x,y) for starting
	private Coordinate end; // coordinate (x,y) for ending

	private List<SubStep> subSteps;

	public Step() {
	}

	public Step(int time, int direction, Coordinate start, Coordinate end) {
		this.time = time;
		this.direction = direction;
		this.start = start;
		this.end = end;
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

	public void setTime(int time) {
		this.time = time;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public List<SubStep> getSubSteps() {
		return subSteps;
	}

	public void setSubSteps(List<SubStep> subSteps) {
		this.subSteps = subSteps;
	}
}
