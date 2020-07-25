package com.reddit;

import com.reddit.Pages.LoginSignupPage;

public class Main {

    public static void main(String[] args) {
        LoginSignupPage loginSignupPage = LoginSignupPage.creatLoginSignupPage();
        loginSignupPage.run();
    }
}
