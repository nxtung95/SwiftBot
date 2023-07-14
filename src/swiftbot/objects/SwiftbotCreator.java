package swiftbot.objects;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class SwiftbotCreator {
	private String name;

	public SwiftbotCreator() {
	}

	public SwiftbotCreator(String name) {
		this.name = name;
	}

	public int createSpeedWheels(int octalNumber) {
		int speed;
		if (octalNumber > 100) { // Set speed = octal - 50 if octal num is greater than 100
			speed = octalNumber - 50;
		} else if (octalNumber < 20) {
			speed = octalNumber + 50;
		} else {
			speed = octalNumber;
		}
		return speed;
	}

	public int createTimeEachStep(String hexaDecimal) {
		int time;
		int length = hexaDecimal.length();
		if (length == 1) {
			time = 1;
		} else {
			time = 2;
		}
		return time;
	}

	public Step createStep(int time, int speed, int digitBinary, Step beforeStep) {
		String color = digitBinary == 1 ? "GREEN" : "";
		Coordinate start = new Coordinate();
		if (beforeStep == null) { // First position of Swiftbot coordinate is (x,y) = (0,0)
			start.setX(0);
			start.setY(0);
		} else {
			// Start position coordinate of the next step is the end position coodirnate of the before step.
			start.setX(beforeStep.getEnd().getX());
			start.setY(beforeStep.getEnd().getY());
		}
		Coordinate end = new Coordinate();
		double distance = ((double) speed / 100 * time);
		if (digitBinary == 1) { // Move forwards
			// Assume that Swiftbot every 1s swiftbot will move a distance 1(m) with max velocity (100%).
			// Example, speed = 94%, time = 2s. Every 1s, swiftbot will move 0.94 (m), total is 1.88 (m).

			//positive
			end.setX(new BigDecimal(start.getX() + distance).setScale(1, RoundingMode.HALF_DOWN).doubleValue());
			end.setY(new BigDecimal(start.getY() + distance).setScale(1, RoundingMode.HALF_DOWN).doubleValue());
		} else { // Move backwards
			// negative
			end.setX(new BigDecimal(start.getX() - distance).setScale(1, RoundingMode.HALF_DOWN).doubleValue());
			end.setY(new BigDecimal(start.getY() - distance).setScale(1, RoundingMode.HALF_DOWN).doubleValue());
		}
		Step step = new Step(time, digitBinary, color, start, end);
		return step;
	}

	public List<Step> createSteps(int time, int speed, String binary) {
		List<Step> stepList = new ArrayList<>();
		int length = binary.length();
		Step beforeStep = null;
		for (int i = length - 1; i >= 0; i-- ) {
			int digitBinary = Integer.valueOf(String.valueOf(binary.charAt(i)));
			Step step = createStep(time, speed, digitBinary, beforeStep);
			beforeStep = step; // Set step which just have created for before step.
			stepList.add(step);
		}
		return stepList;
	}

	public List<AroundObject> createAroundObjects() {
		List<AroundObject> aroundObjects = new ArrayList<>();
		AroundObject obj1 = new AroundObject(new Coordinate(3.4, 3.4));
		AroundObject obj2 = new AroundObject(new Coordinate(-3.4, -3.4));

		aroundObjects.add(obj1);
		aroundObjects.add(obj2);
		return aroundObjects;
	}
}
