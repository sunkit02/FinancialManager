package com.accountants.utils;

import java.util.Scanner;

public class Utils {
    //  TODO: implement regular expression to actually validate the input for strValidation()
    public static String strValidation(String prompt) {
        Scanner scanner = new Scanner(System.in);
        String output;
        do {
            output = "";
            System.out.print(prompt);
            output = scanner.nextLine();
        } while (output.equals(""));
        return output;
    }
    //  Validates use input to be a Double within specified lower and upper bounds
    public static double numValidation(String prompt, double lowerBound, double upperBound) {
        Scanner scanner = new Scanner(System.in);
        double output;
        while(true) {
            System.out.print(prompt);
            try {
                output = scanner.nextDouble();
                if (output <= upperBound && output >= lowerBound) break;
                else System.out.printf("Enter a valid number between %,.2f and %,.2f.\n\r", lowerBound, upperBound);
            } catch (Exception e) {
                System.out.printf("Enter a valid number between %,.2f and %,.2f instead of %s.\n\r", lowerBound, upperBound, scanner.nextLine());
            }
        }
        return output;
    }
    public static String userMaritalStatusInput() {
        final Scanner scanner = new Scanner(System.in);
        String maritalStatus;

        System.out.printf("\t%s\n\t%s\n\t%s\n\t%s\n\t%s\n",
                "1: Single", "2. Married, filling jointly", "3. Married, filling separately",
                "4: Head of household", "5: Widow with dependent child");
        System.out.print("Enter Your Martial Status (corresponding number): ");
        int status = scanner.nextInt();
        // Error Checker
        while (status > 5 || status < 1) {
            System.out.println("Invalid number. Try again.");
            System.out.print("Enter Your Martial Status (corresponding number):");
            System.out.printf("\t%s\n\t%s\n\t%s\n\t%s\n\t%s\n", "1: Single", "2. Married, filling jointly", "3. Married, filling separately",
                    "4: Head of household", "5: Widow with dependent child (for more than 2 years)");

            status = scanner.nextInt();
        }

        switch (status) {
            case 2 -> maritalStatus = "Married and joined";
            case 3 -> maritalStatus = "Married and separated";
            case 4 -> maritalStatus = "Head of household";
            case 5 -> maritalStatus = "Widow";
            default -> maritalStatus = "Single";
        }
        return maritalStatus;
    }
}
