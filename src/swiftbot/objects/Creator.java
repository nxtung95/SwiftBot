package swiftbot.objects;

import java.util.ArrayList;
import java.util.List;

public class Creator {
	private String name;

	public Creator() {
	}

	public Creator(String name) {
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

	public Step createStep(int time, int digitBinary) {
		String color = digitBinary == 1 ? "GREEN" : "";
		Step step = new Step(time, digitBinary, color);
		return step;
	}

	public List<Step> createSteps(int time, String binary) {
		List<Step> stepList = new ArrayList<>();
		int length = binary.length();
		for (int i = length - 1; i >= 0; i-- ) {
			int digitBinary = Integer.valueOf(String.valueOf(binary.charAt(i)));
			Step step = createStep(time, digitBinary);
			stepList.add(step);
		}
		return stepList;
	}
}
