package com.accountants.utils;


import java.util.Arrays;
import java.util.Scanner;

public class InvestmentCalculator {
    // Note: SI = Simple Interest, CI = Compound Interest

    /************************ User Input Methods *************************/

    //  Calculates the total investment after a period of time with a certain interest rate
    //  with compound interest
    public void investmentFuture_CI() {
        double principal = Utils.numValidation(
                "Enter principal: ", 1, 1_000_000);
        double interestRate = Utils.numValidation(
                "Enter annual interest rate: ", 0.01, 10_000) / 100;
        int years = (int) Utils.numValidation(
                "Enter period of investment (years): ", 1, 1_200);
        double futureTotal;
        Scanner scanner = new Scanner(System.in);
        String prompt = """
                [0]     Continuously
                [365]   Daily
                [52]    Weekly
                [26]    Bi-Weekly
                [24]    Semi-Monthly
                [12]    Monthly
                [6]     Bi-Monthly
                [4]     Quarterly
                [2]     Semi-Annually
                [1]     Annually
                Enter compound frequency:\040""";

        //  Ensure that the compound frequency is within the offered options
        Integer[] validInputs = {0, 1, 2, 4, 6, 12, 24, 26, 52, 365};
        int compoundFrequency;
        while (true) {
            System.out.print(prompt);
            try {
                compoundFrequency = scanner.nextInt();
                if (Arrays.asList(validInputs).contains(compoundFrequency)) {
                    break;
                }
                else {
                    System.out.println("Enter a number listed.\n");
                }
            } catch (Exception e) {
                System.out.println("Enter a number listed, not " + scanner.nextLine() + ".");
            }
        }

        //  Calculates using normal compound interest formula except when frequency = 0
        if (compoundFrequency != 0) {
            futureTotal = principal *
                    (Math.pow((1 + (interestRate / compoundFrequency)), years * (double) compoundFrequency));
        }
        //  Calculate with continuous compound interest formula when frequency = 0
        else {
            futureTotal = principal * Math.pow(Math.exp(1), interestRate * years);
        }

        //  Display the results in terminal
        System.out.printf("The projected future total is $%,.2f.%n", futureTotal);
    }

    //  Calculates the total investment after a period of time with a certain interest rate
    //  with simple interest
    public void investmentFuture_SI() {
        double principal = Utils.numValidation(
                "Enter principal: ", 1, 1_000_000);
        double interestRate = Utils.numValidation(
                "Enter annual interest rate: ", 0.01, 10_000);
        int years = (int) Utils.numValidation(
                "Enter period of investment (years): ", 1, 1_200);
        double futureTotal = calculateSimpleInterest(principal, interestRate, years);
        System.out.printf("The projected future total is $%,.2f.%n", futureTotal);
    }

    //  Calculates the time needed to achieve an investment goal with a certain amount of
    //  investment and interest per month
    public void investmentTimeNeeded_SI() {
        final double MONTHS_IN_YEAR = 12;
        double investmentGoal = Utils.numValidation(
                "Enter investment goal: ", 100, 1_000_000);
        double principal = Utils.numValidation(
                "Enter principal: ", 1,1_000_000);
        double interestRate = Utils.numValidation(
                "Enter interest rate: ", 0.01, 10_000) / 100;

        //  Prints time needed in years in terminal
        double timeNeeded = (1/interestRate) * ((investmentGoal / principal) - 1);
        System.out.printf("It will take approximately %,d years or %,d months to reach your goal.%n",
                            Math.round((float) timeNeeded), Math.round((float) timeNeeded * MONTHS_IN_YEAR));
    }

    //  Calculates the interest rate needed to achieve an investment goal with a certain amount of
    //  investment per month and within a certain number of months
    public void investmentInterestNeeded_SI() {
        double investmentGoal = Utils.numValidation(
                "Enter investment Goal: ", 1, 1_000_000);
        double principal = Utils.numValidation(
                "Enter principal: ", 1, 1_000_000);
        double investmentTime = Utils.numValidation(
                "Enter investment duration (years): ", 1, 1_000_000);
        double neededInterestRate =  (1 / investmentTime) * (investmentGoal / principal - 1) * 100;
//        double neededInterestRate =  ((investmentGoal / (monthlyInvestment * investmentTime))*100);
        System.out.println("It will take an annual interest rate of " + String.format("%.2f", neededInterestRate) + "%" +
                            " to reach your goal in " + investmentTime + " year(s).");
    }

    //  TODO: implement a rule of 72 calculator for time and interest to double investment

    /*********************** System Input Methods ************************/
    // Calculates compound interest
    public double calculateCompoundInterest(
            double principal, double interestRate,
            double years, int compoundFrequency) throws Exception {
        Integer[] validInputs = {0, 1, 2, 4, 6, 12, 24, 26, 52, 365};
        double futureTotal;
        interestRate /= 100;
        if (!Arrays.asList(validInputs).contains(compoundFrequency)) {
            throw new Exception( "Valid inputs: " + Arrays.toString(validInputs) +
                    String.format(" not \"%d\".", compoundFrequency));
        }
        if (compoundFrequency != 0) {
            futureTotal = principal * (Math.pow((1 + (interestRate / compoundFrequency)), years * (double) compoundFrequency));
        }
        //  Calculate with continuous compound interest formula when frequency = 0
        else {
            futureTotal = principal * Math.pow(Math.exp(1), interestRate * years);
        }
        return futureTotal;
    }

    public double calculateSimpleInterest (double principal, double interestRate, double years) {
        return principal * (1 + ((interestRate / 100) * years));
    }

}
