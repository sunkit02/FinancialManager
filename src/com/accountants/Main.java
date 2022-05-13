package com.accountants;

import com.accountants.interfaces.UserInterface;

public class Main {
    public static void main(String[] args) throws Exception {
        var FinancialManager = new UserInterface();
        FinancialManager.mainMenu();
    }
}
