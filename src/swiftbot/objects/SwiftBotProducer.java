package swiftbot.objects;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class SwiftBotProducer {
	public static int createSpeed(int octalNumber) {
		int speed;
		// Set speed = octal - 50 if octal num is greater than 100
		if (octalNumber > 100) {
			speed = octalNumber - 50;
		} else if (octalNumber < 20) {
			speed = octalNumber + 50;
		} else {
			speed = octalNumber;
		}
		return speed;
	}

	public static int createTime(String hexaDecimal) {
		int length = hexaDecimal.length();
		return length == 1 ? 1 : 2;
	}

	public static Step createStep(int time, int speed, int digitBinary, Step beforeStep) {
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

		// Assume that Swiftbot every 1s swiftbot will move a distance 1(m) with max velocity (100%).
		// Example, speed = 94%, time = 2s. Every 1s, swiftbot will move 0.94 (m), total is 1.88 (m).
		double[] endCoordinate = createEndCoordinate(digitBinary, start, distance);
		end.setX(endCoordinate[0]);
		end.setY(endCoordinate[1]);

		Step step = new Step(time, digitBinary, start, end);

		// Create sub step if time = 2 seconds:
		if (time != 1) {
			Coordinate startSubStep1 = new Coordinate();
			Coordinate endSubStep1 = new Coordinate();

			Coordinate startSubStep2 = new Coordinate();
			Coordinate endSubStep2 = new Coordinate();

			startSubStep1.setX(start.getX());
			startSubStep1.setY(start.getY());

			double distanceSubStep = distance / 2;
			double[] subEndCoordinate = createEndCoordinate(digitBinary, startSubStep1, distanceSubStep);
			endSubStep1.setX(subEndCoordinate[0]);
			endSubStep1.setY(subEndCoordinate[1]);

			startSubStep2.setX(endSubStep1.getX());
			startSubStep2.setY(endSubStep1.getY());

			subEndCoordinate = createEndCoordinate(digitBinary, startSubStep2, distanceSubStep);
			endSubStep2.setX(subEndCoordinate[0]);
			endSubStep2.setY(subEndCoordinate[1]);

			List<SubStep> subSteps = new ArrayList<>();
			subSteps.add(new SubStep(startSubStep1, endSubStep1));
			subSteps.add(new SubStep(startSubStep2, endSubStep2));

			step.setSubSteps(subSteps);
		}
		return step;
	}

	private static double[] createEndCoordinate(int digitBinary, Coordinate start, double distance) {
		double[] coordinate = new double[2];
		if (digitBinary == 1) {
			coordinate[0] = new BigDecimal(start.getX() + distance).setScale(1, RoundingMode.HALF_DOWN).doubleValue();
			coordinate[1] = (new BigDecimal(start.getY() + distance).setScale(1, RoundingMode.HALF_DOWN).doubleValue());
		} else {
			coordinate[0] = new BigDecimal(start.getX() - distance).setScale(1, RoundingMode.HALF_DOWN).doubleValue();
			coordinate[1] = new BigDecimal(start.getY() - distance).setScale(1, RoundingMode.HALF_DOWN).doubleValue();
		}
		return coordinate;
	}

	public static List<Step> createSteps(int time, int speed, String binary) {
		List<Step> stepList = new ArrayList<>();
		int length = binary.length();
		Step beforeStep = null;
		for (int i = length - 1; i >= 0; i-- ) {
			int digitBinary = Integer.valueOf(String.valueOf(binary.charAt(i)));
			Step step = createStep(time, speed, digitBinary, beforeStep);
			beforeStep = step;
			stepList.add(step);
		}
		return stepList;
	}

	public static List<AroundObject> createAroundObjectsFromFile() throws IOException {
		System.out.println("===== Start initialize the coordinate of some objects for SwiftBot detect");

		List<AroundObject> aroundObjects = new ArrayList<>();
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			File file = new File("./object_file/InitObject.txt");    //creates a new file instance
			fileReader = new FileReader(file);   //reads the file
			bufferedReader = new BufferedReader(fileReader);  //creates a buffering character input stream
			String line = bufferedReader.readLine();
			boolean isFirstLine = true;
			while (line != null) {
				if (isFirstLine) {
					isFirstLine = false;
					line = bufferedReader.readLine();
					continue;
				}
				String[] object = line.split(":");
				String[] coordinate = object[1].split(",");
				AroundObject obj = new AroundObject(object[0], new Coordinate(Double.parseDouble(coordinate[0]), Double.parseDouble(coordinate[1])));
				aroundObjects.add(obj);
				line = bufferedReader.readLine();
			}

		} catch(Exception e) {
			System.out.println("Error read initialize object file!");
		} finally {
			if (fileReader != null) {
				fileReader.close();
			}
			if (bufferedReader != null) {
				bufferedReader.close();
			}
		}
		System.out.println("Total object: " + aroundObjects.size());
		System.out.println("===== End initialize the coordinate of some objects for SwiftBot detect\n");
		return aroundObjects;
	}
}
