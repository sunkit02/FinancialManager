package com.accountants.account;

import com.accountants.utils.InvestmentCalculator;
import com.accountants.utils.Utils;

//  TODO: create update() method that updates this account on the status of its UserAccount
//  TODO: implement methods to output results (get methods and toString() method)
    public class InvestmentAccount {
    InvestmentCalculator calculator = new InvestmentCalculator();
    private String investmentName;

    private String accountNumber;
    private double principal;
    private double investmentInterest;

    //  For JSON read and write
    public InvestmentAccount() {}

    //  Constructor supports either investmentTotal or investmentPercent for instantiation
    public InvestmentAccount(double principal) {
        setInvestmentName();
        setAccountNumber();
        setPrincipal(principal);
        setInvestmentInterest();
    }
    public InvestmentAccount(double income, double investmentPercent) {
        setInvestmentName();
        setAccountNumber();
        setPrincipal(income * investmentPercent);
        setInvestmentInterest();

    }

//    TODO: implement investment calculations

    //  Calculate and display the prediction of current investment
    //  under current interest rate in the next ten years
    public void calculateProjection() throws Exception {
        //  Print header for investment future projection table
        System.out.println(String.format("""

                Future worth of your current investment under current interest rate.
                Principal: $%,.2f\t\tInterest Rate: %.2f""", principal, investmentInterest) + "%");
        System.out.println("Compound Interest: ");
        System.out.println("Year\t\tInvestment Worth");
        //  Calculate and print future investment value with compound interest
        for (int year=1; year < 11; year++) {
            double compoundFuture = calculator.calculateCompoundInterest(
                    principal, investmentInterest,
                    year, 365);
            System.out.printf("%d\t\t\t$%,.2f%n",year, compoundFuture);
        }

        System.out.println("\nSimple Interest: ");
        System.out.println("Year\t\tInvestment Worth");
        //  Calculate and print future investment value with simple interest
        for (int year=1; year < 11; year++) {
            double simpleFuture = calculator.calculateSimpleInterest(
                    principal, investmentInterest,
                    year);
            System.out.printf("%d\t\t\t$%,.2f%n",year, simpleFuture);
        }


    }

    //  Allow user to calculate investment related numbers
    //  (i.e. how long to reach certain investment goal, compound interest,
    //  how much monthly investment needed to reach certain investment gaol, etc.)
//    TODO: finish method to display results in pretty print (finish in InvestmentCalculator class)
    public void investmentDiscovery() {
        int userInput = (int) Utils.numValidation("""
                [1] Compound interest investment calculation
                [2] Simple interest investment calculation
                [3] Simple interest time needed to achieve goal calculation
                [4] Simple interest interest rate needed to achieve goal calculation
                Enter your choice of calculation:\040""", 1, 4);
        switch (userInput) {
            case 1 -> calculator.investmentFuture_CI();
            case 2 -> calculator.investmentFuture_SI();
            case 3 -> calculator.investmentTimeNeeded_SI();
            case 4 -> calculator.investmentInterestNeeded_SI();
        }
    }




    /********************  Get & Set Methods  ********************/
    public void setInvestmentName() {

        this.investmentName = Utils.strValidation("Enter investment name: ");
    }
    public String getInvestmentName() {
        return this.investmentName;
    }

    public void setAccountNumber() {
        this.accountNumber = Utils.strValidation("Enter account number: ");
    }

    public void setPrincipal(double principal) {
        this.principal = principal;
    }
    public double getPrincipal() {
        return this.principal;
    }

    public double getInvestmentInterest() {
        return investmentInterest;
    }

    public void setInvestmentInterest() {
        this.investmentInterest = Utils.numValidation("Enter investment annual interest rate: ", 0.01, 10_000);
    }
    /*************************************************************/

    //  TODO: include future investment potential return etc in method
    @Override
    public String toString() {
        return String.format(
                        "{'investmentName'=%s, 'accountNumber'='%s, 'principal'='$%,.2f', 'interest'='%.2f",
                        investmentName, accountNumber,principal,investmentInterest) + "%'}";
    }
}
