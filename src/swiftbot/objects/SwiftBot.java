package swiftbot.objects;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SwiftBot {
	private List<Step> steps;
	private int speed;

	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_RESET = "\u001B[0m";

	public SwiftBot(List<Step> steps, int speed) {
		this.steps = steps;
		this.speed = speed;
	}

	public void move(List<AroundObject> aroundObjects) throws Exception {
		System.out.println("========= SwiftBot start moving ====================");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
		try {
			for (Step step : steps) {
				if (step.getDirection() == 1) { // Move forwards
					Coordinate start = step.getStart();
					Coordinate end = step.getEnd();
					System.out.println(ANSI_GREEN_BACKGROUND + "Start moving forwards for " + step.getTime() + " seconds, " +
							"Start coordinate (x,y) = (" + start.getX() + "," + start.getY() + ")"
							+ ", End coordinate (x,y) = (" + end.getX() + "," + end.getY() + ")"
							+ ", Speed:" + speed +  ", Start time: " + LocalDateTime.now().format(formatter)
							+ ANSI_RESET);

					if (step.getTime() != 1) { // Have sub steps
						List<SubStep> subSteps = step.getSubSteps();
						for (SubStep subStep : subSteps) {
							Coordinate startSubStep = subStep.getStart();
							Coordinate endSubStep = subStep.getEnd();
							System.out.println(ANSI_GREEN_BACKGROUND + "----Sub step: move from start coordinate (" +
									startSubStep.getX() + "," + startSubStep.getY() + ") to end coordinate (" +
									endSubStep.getX() + "," + endSubStep.getY() + ")" +
									", start time: " + LocalDateTime.now().format(formatter)
									+ ANSI_RESET);
							// Check whether there is an object in front of it within a 1m distance
							boolean detectObject = detectObject(startSubStep, endSubStep, step.getDirection(), aroundObjects);
							if (detectObject) {
								throw new Exception("SwiftBot detects an object in front of it! Stop program!");
							}
							Thread.sleep((step.getTime() / 2) * 1000);
							System.out.println(ANSI_GREEN_BACKGROUND + "----End step, end time: " + LocalDateTime.now().format(formatter)
									+ ANSI_RESET);
						}
					} else {
						// Check whether there is an object in front of it within a 1m distance
						boolean detectObject = detectObject(start, end, step.getDirection(), aroundObjects);
						if (detectObject) {
							throw new Exception("SwiftBot detects an object in front of it! Stop program!");
						}
						Thread.sleep(step.getTime() * 1000);
					}
					System.out.println(ANSI_GREEN_BACKGROUND + "End moving forwards, " +
							", End time: " + LocalDateTime.now().format(formatter)
							+ ANSI_RESET);
				} else { // Move backwards
					Coordinate start = step.getStart();
					Coordinate end = step.getEnd();
					System.out.println("Start moving backwards for " + step.getTime() + " seconds" +
							", Start coordinate (x,y) = (" + start.getX() + "," + start.getY() + ")" +
							", End coordinate (x,y) = (" + end.getX() + "," + end.getY() + ")" +
							", Speed:" + speed +
							", Start time: " + LocalDateTime.now().format(formatter));
					if (step.getTime() != 1) {
						List<SubStep> subSteps = step.getSubSteps();
						for (SubStep subStep : subSteps) {
							Coordinate startSubStep = subStep.getStart();
							Coordinate endSubStep = subStep.getEnd();
							System.out.println("----Sub step: move from start coordinate (" +
									startSubStep.getX() + "," + startSubStep.getY() + ") to end coordinate (" +
									endSubStep.getX() + "," + endSubStep.getY() + ")" +
									", start time: " + LocalDateTime.now().format(formatter)
							);
							// Check whether there is an object in front of it within a 1m distance
							boolean detectObject = detectObject(startSubStep, endSubStep, step.getDirection(), aroundObjects);
							if (detectObject) {
								throw new Exception("SwiftBot detects an object in front of it! Stop program!");
							}
							Thread.sleep((step.getTime() / 2) * 1000);
							System.out.println("----End step, end time: " + LocalDateTime.now().format(formatter));
						}
					} else {
						// Check whether there is an object in front of it within a 1m distance
						boolean detectObject = detectObject(start, end, step.getDirection(), aroundObjects);
						if (detectObject) {
							throw new Exception("SwiftBot detects an object in front of it! Stop program!");
						}
						Thread.sleep(step.getTime() * 1000);
					}
					System.out.println("End moving backwards, End time: " + LocalDateTime.now().format(formatter));
				}
				System.out.println("--------");
			}
		} catch (Exception e) {
			System.err.println("Error SwiftBot moving");
			throw e;
		}
		System.out.println("========= Swiftbot end moving ====================");
	}

	public boolean detectObject(Coordinate start, Coordinate end, int direction, List<AroundObject> aroundObjects) {
		for (AroundObject obj : aroundObjects) {
			double x = obj.getCoordinate().getX(); // Current X coordinate of the object
			double y = obj.getCoordinate().getY(); // Current Y coordinate of the object
			// If there is a object between the distance from start to end
			if (direction == 1) {
				if ((x >= start.getX() && x <= end.getX()) && (y >= start.getY() && y <= end.getY())) {
					System.err.println("There is a object in front of the SwiftBot, coordinate (x,y) = (" + x + "," + y + ")");
					return true;
				}
			} else {
				if ((x <= start.getX() && x >= end.getX()) && (y <= start.getY() && y >= end.getY())) {
					System.err.println("There is a object in front of the SwiftBot, coordinate (x,y) = (" + x + "," + y + ")");
					return true;
				}
			}
		}
		return false;
	}
}
