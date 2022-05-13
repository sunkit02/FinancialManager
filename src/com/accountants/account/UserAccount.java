package com.accountants.account;

import com.accountants.utils.TaxCalculator2022;
import com.accountants.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class UserAccount {
    private final ArrayList<InvestmentAccount> investmentAccounts = new ArrayList<>();

    //  Basic information for account registration
    private String firstName;
    private String lastName;
    private String birthday;
    private String ssn;
    private String userName;
    private String password;

    //  Additional information for tax calculations
    private String maritalStatus;
    private double grossIncome;
    //  Results of tax calculations
    private double netIncome;
    private double taxes;

    //  Investment information
//    TODO: implement a method to update these fields everytime an investment is made
//     (put in end of add investment method)
    private double totalInvestments;
    private double totalInvestmentProfits;


    //  Class Constructors
    //  Constructor for system input
    //  Both constructors automatically calculates taxes based on input information.
    public UserAccount(String firstName, String lastName, String birthday,
                       String ssn, String username, String password,
                       double grossIncome, String maritalStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.ssn = ssn;
        this.userName = username;
        this.password = password;
        this.grossIncome = grossIncome;
        this.maritalStatus = maritalStatus;
        calculateTaxes();
    }

    //  Constructor for user input
    public UserAccount() {
        setFirstName();
        setLastName();
        setBirthday();
        setSsn();
        setUserName();
        setPassword();
        setGrossIncome();
        setMaritalStatus();
        calculateTaxes();
    }

    //  TODO: change to createInvestmentAccount() and make necessary changes
    //  Creates an investment account object, adds it to investmentAccounts list, and returns the new account
    public void createInvestmentAccount() {
        Scanner scanner = new Scanner(System.in);
        InvestmentAccount account;
        double investment;
        int userChoice;
        while (true) {
            System.out.print("""
                    [1] Total Investment Value (Pre-tax)
                    [2] Total Investment Value (Post-tax)
                    [3] Percentage of Gross Income
                    [4] Percentage of Net Income
                    Enter option for total investment entry [1-4]:\s""");
            try {
                //  Check if user input is between integers 1-4
                userChoice = scanner.nextInt();
                Integer[] validChoices = {1, 2, 3, 4};
                if (Arrays.asList(validChoices).contains(userChoice)) break;
                else System.out.println("\nPlease enter an integer between 1 to 4.");
            } catch (Exception e) {
                System.out.printf("\nPlease enter integer between 1 to 4, not \"%s\".%n", scanner.nextLine());
            }
        }
        //  Select the correct combination between pre-tax, post-tax,
        //  income percentage constructor, and total investment constructor.
        //  Add the new investment account to list of investment accounts
        //  and add amount invested into totalInvestments.
        switch (userChoice) {
            case 1 -> {
                investment = Utils.numValidation(
                        "Enter investment total: ", 1, getGrossIncome());
                account = new InvestmentAccount(investment);
                investmentAccounts.add(account);
                totalInvestments += account.getPrincipal();
            }
            case 2 -> {
                investment = Utils.numValidation(
                        "Enter investment total: ", 1, getNetIncome());
                account = new InvestmentAccount(investment);
                investmentAccounts.add(account);
                totalInvestments += account.getPrincipal();

            }
            case 3 -> {
                investment = Utils.numValidation(
                        "Enter investment percentage: ", 0.01, 100) / 100;
                account = new InvestmentAccount(getGrossIncome(), investment);
                investmentAccounts.add(account);
                totalInvestments += account.getPrincipal();
            }
            case 4 -> {
                investment = Utils.numValidation(
                        "Enter investment percentage: ", 0.01, 100) / 100;
                account = new InvestmentAccount(getNetIncome(), investment);
                investmentAccounts.add(account);
                totalInvestments += account.getPrincipal();
            }
        }
    }

    /********************* Tax Calculations  *********************/

    //  Calculate taxes for the account and sets the taxes and netIncome fields based on results of calculations
    public void calculateTaxes() {
        //  Instantiate instance of TaxCalculator for 2022
        TaxCalculator2022 taxCalculator = new TaxCalculator2022(getMaritalStatus(), getGrossIncome());

        //  Calculate taxes with tax calculator
        taxCalculator.calculateTaxes();
        //  Setting tax and netIncome fields according to result of calculations
        this.taxes = taxCalculator.getTaxes();
        this.netIncome = grossIncome - taxes;
    }

    public void displayTaxes() {
        System.out.println(String.format(
                "\nGross Income: $%,.2f\nTaxes: $%,.2f\nNet Income: $%,.2f\nTaxed Percent: %.2f",
                grossIncome, taxes, netIncome,(taxes / grossIncome) * 100) + "%");
    }

    //  TODO: implement method to display all account details including
    //   investments and taxes

    /******************** Get & Set Methods  ********************/

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName() {
        this.firstName = Utils.strValidation("Enter first name: ");
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName() {
        this.lastName = Utils.strValidation("Enter last name: ");
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday() {
        this.birthday = Utils.strValidation("Enter birthday: ");
    }

    public String getSSN() {
        return ssn;
    }

    public void setSsn() {
        this.ssn = Utils.strValidation("Enter Social Security Number: ");
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName() {
        this.userName = Utils.strValidation("Enter username: ");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword() {
        this.password = Utils.strValidation("Enter password: ");
    }
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    //  Ensures that the user is able to produce the old password and
    //  the user's new password is different from the old one
    public void resetPassword() {
        Scanner scanner = new Scanner(System.in);
        String oldPassword = password;
        String newPassword;

        System.out.print("Enter old password: ");
        //  Continues to prompt user to enter the correct old password util it is correct
        while (!scanner.nextLine().equals(oldPassword)) {
            System.out.println("Incorrect, try again.");
            System.out.print("Enter old password: ");
        }
        //  Check if new and old passwords match
        while (true) {
        System.out.print("Enter new password: ");
        newPassword = scanner.nextLine();
        if (newPassword.equals(oldPassword)) System.out.println("Cannot use same password as old password. Please try another one.");
        else break;
        }

        //  Ensure user reenters the new password correctly
        do {
            System.out.print("Enter new password again: ");
        } while (!scanner.nextLine().equals(newPassword));

        setPassword(newPassword);
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    //  Set marital status using static method to ensure correct user input if no input
    public void setMaritalStatus() {
        this.maritalStatus = Utils.userMaritalStatusInput();
    }

    //  Set marital status according to input if there is input
    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public double getGrossIncome() {
        return grossIncome;
    }

    public void setGrossIncome() {

        this.grossIncome = Utils.numValidation("Enter gross income: ", 2, 1_000_000);
    }

    public void setGrossIncome(double grossIncome) {
        this.grossIncome = grossIncome;
    }

    public double getNetIncome() {
        return netIncome;
    }

    public void setNetIncome(double netIncome) {
        this.netIncome = netIncome;
    }

    public double getTaxes() {
        return taxes;
    }

    public void setTaxes(double taxes) {
        this.taxes = taxes;
    }


    public ArrayList<InvestmentAccount> getInvestmentAccounts() {
        return investmentAccounts;
    }

    /*********************************************************************/

    //  Updates all the fields affected by the newest change
    private void updateInvestmentAccount() {
        calculateTaxes();
    }

    //    Displays the full name of user and username of the object when printed

    public void displayUserAccountProfile() {
        //  Mask all digits of social security number except last three before displaying
        StringBuilder maskedSSN = new StringBuilder();
        int displayedSSNLength;
        if (ssn.length() >= 3) displayedSSNLength = 3;
        else displayedSSNLength = ssn.length();
        int maskedSSNLength = ssn.length() - displayedSSNLength;
        for (int i=0; i < maskedSSNLength; i++) {
            maskedSSN.append("*");
        }
        maskedSSN.append(ssn.substring(ssn.length()-displayedSSNLength));

        System.out.printf("""
                                        
                        ************ ACCOUNT PROFILE ***********
                        Username: %s
                                        
                        Name: %s
                        Birthday: %s
                        Social Security: %s
                        Marital Status: %s
                                        
                        Gross Income: $%,.2f
                        Net Income: $%,.2f
                        Taxes: $%,.2f
                                        
                        Total Investments: $%,.2f
                        %n""",
                userName, firstName + ", " + lastName,
                birthday, maskedSSN,maritalStatus,
                grossIncome,netIncome,taxes,totalInvestments);

        System.out.println("Investments:\n");
        int counter = 1;
        for (InvestmentAccount investment: investmentAccounts) {
            System.out.println(String.format("""
                    %d: %s
                    Principal: $%,.2f
                    Interest: %.2f""",
                    counter, investment.getInvestmentName(), investment.getPrincipal()
                    ,investment.getInvestmentInterest()) + "%\n");
            counter++;
        }
    }

    @Override
    public String toString() {
        return String.format(
                "UserAccount{'name'='%s %s', 'username'='%s', 'investments'='%d'",
                firstName, lastName, userName, investmentAccounts.size());
    }
}
