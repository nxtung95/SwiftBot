package swiftbot.objects;

public class NumberConvertor {
	public static String convertDecimalToBinary(int decimal) {
		// Creating a string to store binary number
		String binaryNum = "";

		while (decimal > 0) {
			// storing remainder in binary array
			binaryNum = (decimal % 2) + binaryNum;
			decimal = decimal / 2;
		}
		return binaryNum;
	}

	public static String convertDecimalToHex(int decimal) {
		// Creating a string to store hexadecimal number
		String hexNum = "";

		// counter for hexadecimal number array
		while (decimal != 0) {

			// Storing remainder in hexadecimal array
			int num = decimal % 16;

			// Storing hexadecimal number
			if (num > 9) {
				char hexNumChar = (char)(55 + num); // Because 65(A) - 10 = 55, 10: Number is greater than 9.
				hexNum = hexNumChar + hexNum;
			} else {
				hexNum = num + hexNum;
			}
			decimal = decimal / 16;
		}
		return hexNum;
	}

	public static int convertDecimalToOctal(int decimal) throws NumberFormatException
	{
		// Creating a string to store octal number
		String octalNum = "";

		// counter for octal number array
		while (decimal != 0) {
			// Storing remainder in octal array
			octalNum = (decimal % 8) + octalNum;
			decimal = decimal / 8;
		}
		return Integer.parseInt(octalNum);
	}
}
