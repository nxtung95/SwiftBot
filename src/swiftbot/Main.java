package swiftbot;

import swiftbot.objects.*;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            Scanner keyboard = new Scanner(System.in);
            boolean isContinue = false;
            boolean isFirst = true;
            while (isContinue || isFirst) {
                System.out.println("Enter a decimal number:");

                int decimalNumber;
                try {
                    decimalNumber = keyboard.nextInt();
                } catch (InputMismatchException mismatchException) {
                    System.err.println("Please enter a decimal number, you entered a invalid number!");
                    return;
                }

                if (decimalNumber <= 0 || decimalNumber > 100) {
                    System.err.println("Please enter a decimal number should not be negative and not exceed 100!");
                    return;
                }
                String binary = NumberConvertor.convertDecimalToBinary(decimalNumber);
                String hex = NumberConvertor.convertDecimalToHex(decimalNumber);
                int octal = NumberConvertor.convertDecimalToOctal(decimalNumber);

                System.out.println("Decimal: " + decimalNumber + ", Binary: " + binary + ", Hexadecimal: " + hex + ", Octal: " + octal);

                SwiftbotCreator swiftbotCreator = new SwiftbotCreator("Creator Name");
                int time = swiftbotCreator.createTimeEachStep(hex);
                int speed = swiftbotCreator.createSpeedWheels(octal);
                List<Step> stepList = swiftbotCreator.createSteps(time, speed, binary);

                // Create random the coordinate of Around object position for Swiftbot detect
                List<AroundObject> aroundObjects = swiftbotCreator.createAroundObjects();

                Swiftbot swiftbot = new Swiftbot(stepList, speed);
                try {
                    swiftbot.move(aroundObjects);
                } catch (Exception detechObjectException) {
                    System.err.println("WARNING! " + detechObjectException.getMessage());
                    return;
                }

                System.out.println("Do you want to continue? (Y/N)");
                keyboard.nextLine();
                String answer = keyboard.nextLine();
                if ("Y".equalsIgnoreCase(answer)) {
                    isContinue = true;
                }
                isFirst = false;
            }

        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
