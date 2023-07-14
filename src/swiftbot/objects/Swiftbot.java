package swiftbot.objects;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Swiftbot {
	private List<Step> steps;
	private int speed;

	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_RESET = "\u001B[0m";

	public Swiftbot(List<Step> steps, int speed) {
		this.steps = steps;
		this.speed = speed;
	}

	public void move(List<AroundObject> aroundObjects) throws Exception {
		System.out.println("========= Swiftbot start moving ====================");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
		try {
			for (Step step : steps) {
				if (step.getDirection() == 1) { // Move forwards
					System.out.println(ANSI_GREEN_BACKGROUND + "Start moving forwards for " + step.getTime() + " seconds, " +
							"Start coordinate (x,y) = (" + step.getStart().getX() + "," + step.getStart().getY() + ")"
							+ ", End coordinate (x,y) = (" + step.getEnd().getX() + "," + step.getEnd().getY() + ")"
							+ ", Speed:" + speed +  ", Start time: " + LocalDateTime.now().format(formatter)
							+ ANSI_RESET);
					// Check whether there is an object in front of it within a 1m distance
					boolean detectObject = detectObject(step, aroundObjects);
					if (detectObject) {
						throw new Exception("Swiftbot detects a object on a distanc from start to end");
					}
					Thread.sleep(step.getTime() * 1000);
					System.out.println(ANSI_GREEN_BACKGROUND + "End moving forwards, " +
							", End time: " + LocalDateTime.now().format(formatter)
							+ ANSI_RESET);
				} else { // Move backwards
					System.out.println("Start moving backwards for " + step.getTime() + " seconds" +
							", Start coordinate (x,y) = (" + step.getStart().getX() + "," + step.getStart().getY() + ")" +
							", End coordinate (x,y) = (" + step.getEnd().getX() + "," + step.getEnd().getY() + ")" +
							", Speed:" + speed + ", " +
							", Start time: " + LocalDateTime.now().format(formatter));
					// Check whether there is an object in front of it within a 1m distance
					boolean detectObject = detectObject(step, aroundObjects);
					if (detectObject) {
						throw new Exception("Swiftbot detects a object on a distanc from start to end");
					}
					Thread.sleep(step.getTime() * 1000);
					System.out.println("End moving backwards, End time: " + LocalDateTime.now().format(formatter));
				}
				System.out.println("--------");
			}
		} catch (Exception e) {
			System.err.println("Error Swiftbot moving");
			throw e;
		}
		System.out.println("========= Swiftbot end moving ====================");
	}

	public boolean detectObject(Step step, List<AroundObject> aroundObjects) {
		Coordinate start = step.getStart();
		Coordinate end = step.getEnd();
		for (AroundObject obj : aroundObjects) {
			double x = obj.getCoordinate().getX(); // Current X coordinate of the object
			double y = obj.getCoordinate().getY(); // Current Y coordinate of the object
			// If there is a object between the distance from start to end.
			if (step.getDirection() == 1) {
				if ((x >= start.getX() && x <= end.getX()) && (y >= start.getY() && y <= end.getY())) {
					System.out.println("Detect a object in front of with (x,y) coordinate is (" + x + "," + y + ")");
					return true;
				}
			} else {
				if ((x <= start.getX() && x >= end.getX()) && (y <= start.getY() && y >= end.getY())) {
					System.out.println("Detect a object in front of with (x,y) coordinate is (" + x + "," + y + ")");
					return true;
				}
			}
		}
		return false;
	}
}
