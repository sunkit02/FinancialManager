package com.accountants.account;

import com.accountants.utils.TaxCalculator2022;

import java.util.ArrayList;
import java.util.Scanner;

public class Account {
    //    Static list of accounts already being instantiated
    public static final ArrayList<Account> ACCOUNTS = new ArrayList<>();

//    Declare fields for essential attributes of a financial account
//    TODO: implement pre-tax and post-tax investment options

    private String accountName;
    private String accountNumber;
    private String maritalStatus;
    private double grossIncome;
    private double netIncome;
    private double taxes;
    private float savingsPercent;
    private float investmentPercent;
    private double savings;
    private double investments;

    //    Get user input for marital status
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
//        TODO: change default to catch invalid inputs and add a case 6 for divorced
        switch (status) {
            case 2 -> maritalStatus = "Married and joined";
            case 3 -> maritalStatus = "Married and separated";
            case 4 -> maritalStatus = "Head of household";
            case 5 -> maritalStatus = "Widow";
            default -> maritalStatus = "Single";
        }
        return maritalStatus;
    }

    //    Constructor for an account

    public Account(String accountName, String accountNumber, String maritalStatus,
                   double grossIncome, float savingsPercent, float investmentPercent) {
        setAccountName(accountName);
        setAccountNumber(accountNumber);
        setMaritalStatus(maritalStatus);
        setGrossIncome(grossIncome);
        setSavingsPercent(savingsPercent);
        setInvestmentPercent(investmentPercent);
        setSavingsPercent(savingsPercent);

        // Add new account to list of existing accounts
        Account.ACCOUNTS.add(this);

    }

    //  Display account in nicer format when printed
    @Override
    public String toString() {
        return String.format("""
                        Name: %s %s
                        Gross Income: $%.2f
                        Savings Percentage: %.2f
                        Investment Percentage: %.2f
                        Savings: $%.2f
                        Investments: $%.2f%n""",
                accountName, accountNumber, grossIncome, savingsPercent * 100,
                investmentPercent * 100, savings, investments);
    }

    //  Calculate taxes for the account and sets the taxes and netIncome fields based on results of calculations
    public void calculateTaxes() {
        //    Instantiate instance of TaxCalculator for 2022
        TaxCalculator2022 taxCalculator = new TaxCalculator2022(getMaritalStatus(), getGrossIncome());
        taxCalculator.calculateTaxes();
        this.taxes = taxCalculator.getTaxes();
        this.netIncome = grossIncome - taxes;
    }

    /***********************Get & Set Methods***********************/

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getGrossIncome() {
        return grossIncome;
    }

    public void setGrossIncome(double grossIncome) {
        this.grossIncome = grossIncome;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    //  Set marital status using static method to ensure correct user input if no input
    public void setMaritalStatus() {
        this.maritalStatus = Account.userMaritalStatusInput();
    }

    //  Set marital status according to input if there is input
    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public float getSavingsPercent() {
        return savingsPercent;
    }

    // Setting savings rate and calculating and setting savings percentage based on
    //  ratio between gross income and savings rate
    public void setSavingsPercent(float savingsPercent) {
        this.savingsPercent = savingsPercent / 100;
        this.savings = grossIncome * savingsPercent;
    }

    public float getInvestmentPercent() {
        return investmentPercent;
    }

    //  Automatically changes total of investments when there is a change in investment percentage
    public void setInvestmentPercent(float investmentPercent) {
        this.investmentPercent = investmentPercent / 100;
        setInvestments();
    }

    public void setInvestments() {
        this.investments = investmentPercent * grossIncome;
    }

    public double getTaxes() {
        return taxes;
    }

    public double getInvestments() {
        return investments;
    }

    public double getNetIncome() {
        return netIncome;
    }


}
