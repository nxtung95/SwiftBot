package swiftbot;

import swiftbot.objects.Creator;
import swiftbot.objects.NumberConvertor;
import swiftbot.objects.Step;
import swiftbot.objects.Swiftbot;

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

                Creator creator = new Creator("Creator Name");
                int time = creator.createTimeEachStep(hex);
                int speed = creator.createSpeedWheels(octal);
                List<Step> stepList = creator.createSteps(time, binary);

                Swiftbot swiftbot = new Swiftbot(stepList, speed);
                swiftbot.move();

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
