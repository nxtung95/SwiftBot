package swiftbot;

import swiftbot.objects.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SwiftBotMain {

    public static void main(String[] args) {
        Scanner keyboard = null;
        try {
            keyboard = new Scanner(System.in);
            String continueAnswer = "Y";
            boolean isFirstInitObject = true;
            List<AroundObject> aroundObjects = new ArrayList<>();
            while ("Y".equalsIgnoreCase(continueAnswer)) {
                System.out.println("Enter a decimal number:");

                int decimalNumber;
                try {
                    decimalNumber = inputDecimalNumber(keyboard);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    return;
                }
                String binary = NumberConvertor.convertDecimalToBinary(decimalNumber);
                String hexadecimal = NumberConvertor.convertDecimalToHex(decimalNumber);
                int octal = NumberConvertor.convertDecimalToOctal(decimalNumber);
                System.out.println("Decimal: " + decimalNumber + ", Binary: " + binary + ", Hexadecimal: " + hexadecimal + ", Octal: " + octal + "\n");

                // Create the coordinate of around object for SwiftBot detect from file
                if (isFirstInitObject) {
                    aroundObjects = SwiftBotProducer.createAroundObjectsFromFile();
                    isFirstInitObject = false;
                }

                int time = SwiftBotProducer.createTime(hexadecimal);
                int speed = SwiftBotProducer.createSpeed(octal);
                List<Step> stepList = SwiftBotProducer.createSteps(time, speed, binary);

                SwiftBot swiftbot = new SwiftBot(stepList, speed);
                try {
                    swiftbot.move(aroundObjects);
                } catch (Exception detechObjectException) {
                    System.err.println("WARNING! " + detechObjectException.getMessage());
                    return;
                }

                System.out.println("Do you want to continue? (Y/N)");
                keyboard.nextLine();
                continueAnswer = keyboard.nextLine();
            }

        } catch (Exception e) {
            System.err.println(e);
        } finally {
            if (keyboard != null) {
                keyboard.close();
            }
        }
    }

    private static int inputDecimalNumber(Scanner keyboard) throws Exception {
        int decimalNumber;
        String errorMessage;
        try {
            decimalNumber = keyboard.nextInt();
        } catch (InputMismatchException e) {
            errorMessage = "You entered a invalid number!";
            throw new InputMismatchException(errorMessage);
        }
        if (decimalNumber <= 0 || decimalNumber > 100) {
            errorMessage = "You entered a decimal number be negative and exceed 100!";
            throw new Exception(errorMessage);
        }
        return decimalNumber;
    }
}
