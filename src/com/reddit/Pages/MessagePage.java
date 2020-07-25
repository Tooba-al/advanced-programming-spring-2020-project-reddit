package com.reddit.Pages;

import com.reddit.Modules.MessageManagement.*;
import com.reddit.Modules.UserManagement.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class MessagePage {
    private User currentUser;
    private Scanner scanner = new Scanner(System.in);
    private Message message = new Message();

    public MessagePage(User currentUser) {
        this.currentUser = currentUser;
    }

    // add receiver
    private void addReceiver() {
        System.out.println("1. Add user\n2. Back");
        System.out.println();
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice){
                       case 1:
                System.out.print("Enter the username: ");
                String username = scanner.nextLine();
                ArrayList<User> users = User.searchUser(username);
                int count = 1;
                for (User u : users) {
                    System.out.println(count + ". " + u.getUsername());
                }
                System.out.println();
                System.out.print("Choose a number: ");
                choice = scanner.nextInt();
                scanner.nextLine();
                User user = users.get(choice-1);
                MessageRole messageRole = message.getMessagableRole(user);
                if (messageRole != null) {
                    System.out.println("User is already in receiver list!");
                } else {
                    MessagePersonRelation messagePersonRelation = new MessagePersonRelation();
                    messagePersonRelation.setRole(MessageRole.Receiver);
                    messagePersonRelation.setMessagable(user);
                    message.getMessagePersonRelations().add(messagePersonRelation);
                }
                break;
            case 2:
                return;
            default:
                addReceiver();
                break;
        }
    }

    // send
    private Message send(MessageType messageType) {
        if (message.getMessageData() == null) {
            System.out.println("The message should have message data");
            return null;
        } else if (message.getMessagePersonRelations().isEmpty()) {
            System.out.println("You should set receivers for the message");
            return null;
        }
        MessagePersonRelation messagePersonRelation = new MessagePersonRelation();
        messagePersonRelation.setMessagable(currentUser);
        messagePersonRelation.setRole(MessageRole.Sender);
        message.getMessagePersonRelations().add(messagePersonRelation);
        message.setMessageType(messageType);
        message.setCalendar(Calendar.getInstance());
        message.sendMessage();
        return message;
    }

    // show message
    private void showMessage() {
        if (message.getMessageData() == null) {
            System.out.println("No data!");
        } else {
            System.out.println(message.getMessageData().show());
        }
        if (message.getMessagePersonRelations().isEmpty()) {
            System.out.println("No receiver added!");
        } else {
            int i = 1;
            for (MessagePersonRelation messagePersonRelation : message.getMessagePersonRelations()) {
                System.out.println(i + ". " + messagePersonRelation.getMessagable().getString());
                ++i;
            }
        }
    }

    // run
    public Message run(MessageType messageType) {
        while (true) {
            showMessage();
            System.out.println("1. Add receivers\n2. Send a message\n3. Back to home page");
            System.out.println();
            System.out.print("Choose a number: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice){
                case 1:
                    addReceiver();
                    break;
                case 2:
                    Message message = send(messageType);
                    if (message != null) {
                        return message;
                    }
                    break;
                case 3:
                    HomePage homePage = HomePage.createHomePage(currentUser);
                    homePage.run();
                    break;
                default:
                    run(messageType);
            }
        }
    }

}
