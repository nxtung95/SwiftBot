package swiftbot.objects;

public class Step {
	private int time;
	private int direction; //0: Move backwards, 1: Move forward
	private String color;

	public Step() {
	}

	public Step(int time, int direction, String color) {
		this.time = time;
		this.direction = direction;
		this.color = color;
	}

	public int getTime() {
		return time;
	}

	public int getDirection() {
		return direction;
	}

	public String getColor() {
		return color;
	}
}
