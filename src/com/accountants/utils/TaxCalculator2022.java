package com.accountants.utils;

public class TaxCalculator2022 {
    public double deductions;
    private String maritalStatus;
    private final double[] taxRate = {.1, .12, .22, .24, .32, .35, .37};
    private double grossIncome;
    private double taxableIncome;
    private double taxes;

    //    Constructor for tax calculator that takes in marital status and gross income
//    from the account that is being used
    public TaxCalculator2022(String maritalStatus, double grossIncome) {
        setMaritalStatus(maritalStatus);
        setGrossIncome(grossIncome);
        setDeductions();
        setTaxableIncome();
    }

    //    Select the correct calculator for tax calculation
    public void calculateTaxes() {
        double taxes = 0;
        switch (getMaritalStatus()) {
            case "Single", "Married and separate" -> {
                taxes = calculateSingleBracket();
            }
            case "Married and joined" -> {
                taxes = calculateMarriedJointBracket();
            }
            case "Head of household" -> {
                taxes = calculateHeadOfHouseBracket();
            }
        }
    }

    //    Tax calculations for individuals that are single or married and separate
    private double calculateSingleBracket() {
        final double[] taxableSingleIncome = {10_275, 41_775, 89_075, 170_050, 215_950, 539_900};
        // Calculate taxes for people that file as single and Married and Separate
        // Single and Married filling separately are very similar, they aren't different till the 35% bracket
        if (taxableIncome <= taxableSingleIncome[0]) {
            taxes = taxRate[0] * taxableIncome;
        } else if (taxableIncome <= taxableSingleIncome[1]) {
            taxableIncome -= taxableSingleIncome[0];
            taxes = 1_027.5 + taxRate[1] * taxableIncome;
        } else if (taxableIncome <= taxableSingleIncome[2]) {
            taxableIncome -= taxableSingleIncome[1];
            taxes = 4_807.5 + taxRate[2] * taxableIncome;
        }
        else if (taxableIncome <= taxableSingleIncome[3]) {
            taxableIncome -= taxableSingleIncome[2];
            taxes = 15_213.5 + taxRate[3] * taxableIncome;
        } else if (taxableIncome <= taxableSingleIncome[4]) {
            taxableIncome -= taxableSingleIncome[3];
            taxes = 34_647.5 + taxRate[4] * taxableIncome;
        } else if (taxableIncome <= taxableSingleIncome[5] && maritalStatus.equals("Single")) {
            taxableIncome -= taxableSingleIncome[4];
            taxes = 49_335.5 + taxRate[5] * taxableIncome;
        } else if (taxableIncome <= taxableSingleIncome[5] && maritalStatus.equals("Married and separate")) {
            taxableIncome -= 215_950;
            taxes = 49_335.5 + taxRate[5] * taxableIncome;
        }else if (taxableIncome > taxableSingleIncome[5] && maritalStatus.equals("Single")){
            taxableIncome -= taxableSingleIncome[5];
            taxes = 162_718 + taxRate[6] * taxableIncome;
        } else if (taxableIncome > 323_925 && maritalStatus.equals("Married and separate")) {
            taxableIncome -= 323_925;
            taxes = 87_126.75 + taxRate[6] * taxableIncome;
        }
        return taxes;
    }

    //    Tax calculations for individuals that are filing married and joined
    private double calculateMarriedJointBracket() {
        final double[] taxableMarriedJointIncome = {20_550, 83_550, 178_150, 340_100, 431_900, 647_850};

        // Calculate taxes for people that file as Married and Joint
        if (taxableIncome <= taxableMarriedJointIncome[0]) {
            taxes = taxRate[0] * taxableIncome;
        } else if (taxableIncome <= taxableMarriedJointIncome[1]) {
            taxableIncome -= taxableMarriedJointIncome[0];
            taxes = 2_055 + taxRate[1] * taxableIncome;
        } else if (taxableIncome <= taxableMarriedJointIncome[2]) {
            taxableIncome -= taxableMarriedJointIncome[1];
            taxes = 9_615 + taxRate[2] * taxableIncome;
        }
        else if (taxableIncome <= taxableMarriedJointIncome[3]) {
            taxableIncome -= taxableMarriedJointIncome[2];
            taxes = 30_427 + taxRate[3] * taxableIncome;
        } else if (taxableIncome <= taxableMarriedJointIncome[4]) {
            taxableIncome -= taxableMarriedJointIncome[3];
            taxes = 69_295 + taxRate[4] * taxableIncome;
        } else if (taxableIncome <= taxableMarriedJointIncome[5]) {
            taxableIncome -= taxableMarriedJointIncome[4];
            taxes = 98_671 + taxRate[5] * taxableIncome;
        } else {
            taxableIncome -= taxableMarriedJointIncome[5];
            taxes = 174_253.5 + taxRate[6] * taxableIncome;
        }
        return taxes;
    }

    //    Tax calculations for individual that are head of household
    private double calculateHeadOfHouseBracket() {
        final double[] taxableHeadOfHouseIncome = {14_650, 55_900, 89_050, 170_050, 215_950, 539_900};

        // Calculate taxes for people that file as Head of House
        if (taxableIncome <= taxableHeadOfHouseIncome[0]) {
            taxes = taxRate[0] * taxableIncome;
        } else if (taxableIncome <= taxableHeadOfHouseIncome[1]) {
            taxableIncome -= taxableHeadOfHouseIncome[0];
            taxes = 1_465 + taxRate[1] * taxableIncome;
        } else if (taxableIncome <= taxableHeadOfHouseIncome[2]) {
            taxableIncome -= taxableHeadOfHouseIncome[1];
            taxes = 6_415 + taxRate[2] * taxableIncome;
        }
        else if (taxableIncome <= taxableHeadOfHouseIncome[3]) {
            taxableIncome -= taxableHeadOfHouseIncome[2];
            taxes = 13_708 + taxRate[3] * taxableIncome;
        } else if (taxableIncome <= taxableHeadOfHouseIncome[4]) {
            taxableIncome -= taxableHeadOfHouseIncome[3];
            taxes = 33_148 + taxRate[4] * taxableIncome;
        } else if (taxableIncome <= taxableHeadOfHouseIncome[5]) {
            taxableIncome -= taxableHeadOfHouseIncome[4];
            taxes = 47_836 + taxRate[5] * taxableIncome;
        } else {
            taxableIncome -= taxableHeadOfHouseIncome[5];
            taxes = 161_218.5 + taxRate[6] * taxableIncome;
        }
        return taxes;
    }

    /************************Get & Set Methods***********************/

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getMaritalStatus() {
        return this.maritalStatus;
    }

    public void setGrossIncome(double grossIncome) {
            this.grossIncome = grossIncome;
    }

    public void setDeductions() {
        // case single, Married and separate
        switch (getMaritalStatus()) {
            case "Head of household" -> deductions = 19_400;
            case "Married and joined", "Widow" -> deductions = 25_900;
            default -> deductions = 12_950;
        }
    }

    private void setTaxableIncome() {
        this.taxableIncome = this.grossIncome - this.deductions;
    }

    public double getTaxes() {
        return this.taxes;
    }
}
