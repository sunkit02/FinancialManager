package com.accountants.interfaces;


import com.accountants.account.InvestmentAccount;
import com.accountants.account.UserAccount;
import com.accountants.utils.Values;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

//  TODO: Potentially implement a file that can be bundled into jar file that stores
//   the admin login information or even bundle all database files into the jar file.
public class UserInterface {

/********************************************** Field Declaration *****************************************************/

    private final ArrayList<UserAccount> userAccounts = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    //    Condition for terminating forever loop running the program
    private enum ProgramState {RUN, END}

    private enum LoginStatus {LOGGED_OUT, USER_ACCOUNT, INVESTMENT_ACCOUNT}

    private ProgramState programState = ProgramState.RUN;
    private LoginStatus loginStatus = LoginStatus.LOGGED_OUT;

/************************************************** Main Menu *********************************************************/

    public void mainMenu() throws Exception {
        printWelcome();
        while (programState != ProgramState.END) {
//            Prompt for user input
            printPrompt("");
//            Clean up user input if needed by removing white spaces and switching to lower case
            String userInput = scanner.nextLine().trim().toLowerCase();

//            Main menu for user interface
            switch (userInput) {
                case "exit" -> programState = ProgramState.END;
                case "help" -> printHelpMenu();
                case "login" -> {
                    //  Login interface returns boolean value representing login success or failure
                    //  and the UserAccount logged in if login was successful
                    Values login = login_Interface();
                    if (login.isLoggedIn()) {
                        manageUserAccount_Menu(login.getUserAccount());
                    }
                }
                case "account new" -> createUserAccount_Interface();

//                for debugging purposes
                case "debug" -> {
                    userAccounts.add(new UserAccount(
                            "Sun Kit", "Tsui", "Feb 28, 2002",
                            "123-45-6789", "sunkit", "123456",
                            250000, "Single"));
                    userAccounts.add(new UserAccount(
                            "Eunice", "Tan", "April 11, 2002",
                            "123-45-6789", "eunice", "123456",
                            300000, "Single"));
                }
                case "useraccounts" -> System.out.println(userAccounts);
                default -> System.out.println("There is no such command: " + userInput);
            }
        }
    }


/****************************************** Submenus (NEED printPrompt) ***********************************************/

    private void manageUserAccount_Menu(UserAccount currentUserAccount) throws Exception {
        loginStatus = LoginStatus.USER_ACCOUNT;
        String currentPrompt = String.format("/%s", currentUserAccount.getUserName());
//        Print login success message
        System.out.println("\nYou have successfully logged in, " + currentUserAccount.getFirstName() + " !");
        System.out.println("Please enter your command");
        while (loginStatus == LoginStatus.USER_ACCOUNT) {
//            Print prompt for user input
            printPrompt(currentPrompt);
            String userInput = scanner.nextLine().toLowerCase().trim();

            switch (userInput) {

                case "help" -> printHelpMenu();
                case "my profile" -> currentUserAccount.displayUserAccountProfile();
                case "new investment" -> currentUserAccount.createInvestmentAccount();
                case "my investments" -> {
                    //  Print out all the existing InvestmentAccounts if there are any
                    if (currentUserAccount.getInvestmentAccounts() != null) {
                        for (InvestmentAccount account : currentUserAccount.getInvestmentAccounts()) {
                            System.out.println(account);
                        }
                    }
                    else {
                        System.out.println("You do not have any investments.");
                    }
                }

                case "mng investments" -> {
                    InvestmentAccount selectedAccount = selectInvestmentAccount_Interface(currentUserAccount);
                    if (selectedAccount != null) {
                        manageInvestmentAccount_Menu(selectedAccount, currentPrompt);
                    } else {
                        System.out.println("You do not have any investment accounts.");
                    }
                }
                case "logout" -> {
                    loginStatus = LoginStatus.LOGGED_OUT;
                    System.out.println("You have successfully logged out!\n");
                }
                case "del account" -> {
                    System.out.print("Are you sure you want to delete your user account? y/[n] ");
                    String userDecision = scanner.nextLine().toLowerCase().trim();
                    if (userDecision.equals("y")) {
                        userAccounts.remove(currentUserAccount);
                        loginStatus = LoginStatus.LOGGED_OUT;
                    }
                }

                case "set name" -> {
                    currentUserAccount.setFirstName();
                    currentUserAccount.setLastName();
                }
                case "set income" -> {
                    currentUserAccount.setGrossIncome();
                    currentUserAccount.calculateTaxes();
                }
                case "set marital" -> {
                    currentUserAccount.setMaritalStatus();
                    currentUserAccount.calculateTaxes();
                }
                case "set birthday" -> currentUserAccount.setBirthday();
                case "set username" -> currentUserAccount.setUserName();
                case "set password" -> currentUserAccount.resetPassword();
                case "taxes" -> currentUserAccount.displayTaxes();
                default -> System.out.println("There is no such command: " + userInput);
            }
        }
    }

    private void manageInvestmentAccount_Menu(InvestmentAccount currentInvestment, String prompt) throws Exception {
        loginStatus = LoginStatus.INVESTMENT_ACCOUNT;
        //  Update prompt to reflect loginStatus change
        String currentPrompt = String.format("%s/%s", prompt, currentInvestment.getInvestmentName());

        // Menu for investmentAccount operations
        while (loginStatus == LoginStatus.INVESTMENT_ACCOUNT) {
            printPrompt(currentPrompt);
            String userInput = scanner.nextLine().toLowerCase().trim();
            switch (userInput) {
                case "help" -> printHelpMenu();
                case "investment future" -> currentInvestment.calculateProjection();
                case "calculator" -> currentInvestment.investmentDiscovery();
                case "exit investment" -> loginStatus = LoginStatus.USER_ACCOUNT;
                default -> System.out.println("There is no such command: " + userInput);
            }
        }
    }

/********************************************** Functional Interfaces *************************************************/

//    TODO: add limited tries to login
    private Values login_Interface() {
        while (true) {
//            Get user input for username and password
            System.out.print("Username: ");
            String username = scanner.nextLine().trim();
            System.out.print("Password: ");
            String password = scanner.nextLine().trim();
//            Loop through all valid pairs and compare with user input
            for (UserAccount userAccount : userAccounts) {
                if (username.equals(userAccount.getUserName()) &&
                        password.equals(userAccount.getPassword())) {
//                    return true and the user account that matches if there is a match
                    return new Values(true, userAccount);
                }
            }

//            Checks if user wants to continue logging in if above statement not true
            System.out.print("Username or password is incorrect\nTry again? [y]/n: ");
            String userInput = scanner.nextLine().trim().toLowerCase();
            if (userInput.equals("") || userInput.equals("y")) continue;
            break;
        }
//        return false to signify failed login attempt if no matching username passwords are identified
//        and a null placeholder for matching account
        return new Values(false, null);
    }

    private void createUserAccount_Interface() {
//        Create UserAccount with user input
        UserAccount newUserAccount = new UserAccount();
//        Automatically adds new user account to database/userAccounts arraylist
        userAccounts.add(newUserAccount);
    }

    //  TODO: add exit mechanism during investment account selection phase
    private InvestmentAccount selectInvestmentAccount_Interface(UserAccount currentUserAccount) {
//        print list of existing accounts for the current user account
        if (currentUserAccount.getInvestmentAccounts() != null) {

            System.out.println(currentUserAccount.getInvestmentAccounts());
            System.out.print("Please enter investment name: ");
            while (true) {
                String selectedInvestmentName = scanner.nextLine().trim();
                for (InvestmentAccount account : currentUserAccount.getInvestmentAccounts()) {
                    if (account.getInvestmentName().equals(selectedInvestmentName)) return account;
                }
                System.out.print("This account doesn't exist, try again? [y]/n ");
                String userDecision = scanner.nextLine().toLowerCase().trim();
                if (userDecision.equals("n")) break;
            }
        }
        return null;
    }



/************************************************ Helper Methods ******************************************************/

    //  Prompt for user input
    private void printPrompt(String accountLoggedIn) {
        System.out.printf("\nFinancial Manager%s >", accountLoggedIn);
    }

    //  Intro to the program
    private void printWelcome() {
        System.out.println("Welcome to the Financial Manger!\nPlease enter your command");
    }

    //  Help menu with all the commands
    private void printHelpMenu() {
        switch (loginStatus) {
            case LOGGED_OUT -> System.out.println("""
                    Commands Available:
                    help                Display help menu
                    login               Login to user account
                    account new         Create new user account
                    exit                Terminate program""");
            //  TODO: add option to delete investments
            case USER_ACCOUNT -> System.out.println("""
                    Commands Available:
                    help                Display help menu
                    my profile          Display account profile
                    new investment      Create new investment account
                    my investments      View financial accounts in user account
                    mng investments     Select financial account to manage
                    logout              Logout of user account
                    del account         Delete current user account
                    
                    set name            Change first and last name
                    set income          Change gross income
                    set marital         Change marital status
                    set birthday        Change birthday
                    set username        Change account username
                    set password        Reset account password
                    taxes               Display tax information
                    """);
            case INVESTMENT_ACCOUNT -> System.out.println("""
                    Commands Available:
                    help                Display help menu
                    investment future   Display projected investment value for 10 yrs
                    calculator          Access various investment calculators
                    exit investment     Return to UserAccount interface""");
        }
    }

}
