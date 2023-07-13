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

	public void move() {
		System.out.println("========= Swiftbot start moving ====================");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
		try {
			for (Step step : steps) {
				if (step.getDirection() == 1) { // Move forwards
					System.out.println(ANSI_GREEN_BACKGROUND + "Start moving forwards for " + step.getTime()
							+ " seconds, Speed:" + speed +  ", Start time: " + LocalDateTime.now().format(formatter)
							+ ANSI_RESET);
					Thread.sleep(step.getTime() * 1000);
					System.out.println(ANSI_GREEN_BACKGROUND + "End moving forwards, End time: "
							+ LocalDateTime.now().format(formatter)
							+ ANSI_RESET);
				} else { // Move backwards
					System.out.println("Start moving backwards for " + step.getTime()
							+ " seconds, Speed:" + speed + ", Start time: " + LocalDateTime.now().format(formatter));
					Thread.sleep(step.getTime() * 1000);
					System.out.println("End moving backwards, End time: " + LocalDateTime.now().format(formatter));
				}
				System.out.println("--------");
			}
		} catch (Exception e) {
			System.err.println("Error Swiftbot moving");
		}
		System.out.println("========= Swiftbot end moving ====================");
	}
}
