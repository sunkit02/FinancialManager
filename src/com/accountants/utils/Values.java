package com.accountants.utils;

import com.accountants.account.UserAccount;

public class Values {
    boolean loginState;
    UserAccount userAccount;

    public Values (boolean loginState, UserAccount userAccount) {
        this.loginState = loginState;
        this.userAccount = userAccount;
    }

    public boolean isLoggedIn() {
        return loginState;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }
}
