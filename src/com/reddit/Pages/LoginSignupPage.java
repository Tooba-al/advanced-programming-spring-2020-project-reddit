package com.reddit.Pages;

import com.reddit.Modules.UserManagement.User;

import java.util.Scanner;

public class LoginSignupPage {
    private static Scanner scanner = new Scanner(System.in);
    public LoginSignupPage() {
    }

    // Create LoginSignupPage
    public static LoginSignupPage creatLoginSignupPage(){
        return new LoginSignupPage();
    }

    // Login
    private static void login(){
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = User.login(username, password);
        if (user == null){
            System.out.println("There is no account with this name!");
            System.out.println();
            run();
        }
        else {
            HomePage homePage = HomePage.createHomePage(user);
            homePage.run();
        }
    }

    // Signup
    public static void signup(){
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        User user = User.signup(username, password, email);
        if (user == null){
            run();
        }
        else {
            HomePage homePage = HomePage.createHomePage(user);
            homePage.run();
        }
    }

    // run
    public static void run(){
        //Clean Console
        System.out.println("\033[H\033[2J");
        System.out.flush();

        System.out.println("    [Login / Signup]    ");
        System.out.println("------------------------");
        System.out.println("1. Login\n2. Sign up\n3. Exit");
        System.out.println();
        System.out.print("Choose a number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice){
            case 1:
                login();
                break;
            case 2:
                signup();
                break;
            case 3:
                return;
            default:
                run();
                break;
        }
    }
}
