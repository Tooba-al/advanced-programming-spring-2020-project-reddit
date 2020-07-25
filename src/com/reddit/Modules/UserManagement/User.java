package com.reddit.Modules.UserManagement;

import com.reddit.Modules.MessageManagement.Messagable;
import com.reddit.Modules.SubredditManagement.Post;
import com.reddit.Modules.SubredditManagement.Subreddit;

import java.util.ArrayList;
import java.util.Scanner;

public class User implements Messagable {
    private String username;
    private String password;
    private String email;
    public static ArrayList<User> userDataBase = new ArrayList<>();
    public static ArrayList<User> userRelationUser = new ArrayList<>();
    private static ArrayList<Subreddit> userSubreddits = new ArrayList<>();
    private static ArrayList<Post> sharedPosts = new ArrayList<>();
    private ArrayList<Subreddit> userRelations = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public static ArrayList<User> getUserDataBase() {
        return userDataBase;
    }
    public static void setUserDataBase(ArrayList<User> userDataBase) {
        User.userDataBase = userDataBase;
    }
    public ArrayList<Subreddit> getUserSubreddits() {
        return userSubreddits;
    }
    public void setUserSubreddits(ArrayList<Subreddit> userSubreddits) {
        this.userSubreddits = userSubreddits;
    }
    public ArrayList<Subreddit> getUserRelations() {
        return userRelations;
    }
    public void setUserRelations(ArrayList<Subreddit> userRelations) {
        this.userRelations = userRelations;
    }
    public static ArrayList<User> getUserRelationUser() {
        return userRelationUser;
    }
    public static void setUserRelationUser(ArrayList<User> userRelationUser) {
        User.userRelationUser = userRelationUser;
    }
    //    public static Post[] getSharedPosts() {
//        return sharedPosts;
//    }
//    public static void setSharedPosts(Post[] sharedPosts) {
//        User.sharedPosts = sharedPosts;
//    }
    public static ArrayList<Post> getSharedPosts() {
        return sharedPosts;
    }
    public static void setSharedPosts(ArrayList<Post> sharedPosts) {
        User.sharedPosts = sharedPosts;
    }

    @Override
    public String getString() {
        return username;
    }

    // create user
    public static User createUser(String username, String password, String email){
        return new User(username, password, email);
    }

    // follow
    public void follow(User currentUser, User user){
        currentUser.getUserRelationUser().add(user);
        System.out.println("You follow *" + user.getUsername() + "*");
    }

    // unfollow
    public void unfollow(User currentUser, User user){
        for (User user1 : currentUser.getUserRelationUser()){
            if (user1 == user){
                currentUser.getUserRelationUser().remove(user);
            }
        }
        System.out.println("You unfollow *" + user.getUsername() + "*");
    }

    // join
    public void join(User user, Subreddit subreddit){
        user.userRelations.add(subreddit);
        System.out.println("You Join " + subreddit.getSubredditName());
    }

    // leave
    public void leave(User user, Subreddit subreddit){
        user.userRelations.remove(subreddit);
        System.out.println("You Leave *" + subreddit.getSubredditName() + "*");
    }

    // hass been join
    public boolean hasJoined(User user, Subreddit subreddit){
        for (Subreddit searchSubreddit : userRelations){
            if (searchSubreddit == subreddit){
                return true;
            }
        }
        return false;
    }

    // login
    public static User login(String username, String password){
        for (User user : userDataBase){
            if (user.getUsername().equals(username)){
                if (user.getPassword().equals(password)){
                    return user;
                }
                System.out.println("Wrong password! Please try again...");
                String newPassword = scanner.nextLine();
                login(username, newPassword);
            }
        }
        return null;
    }

    // signup
    public static User signup(String username, String password, String email){
        for (User user : userDataBase){
            if (user.getUsername().equals(username)){
                return null;
            }
        }
        User newUser = User.createUser(username, password, email);
        userDataBase.add(newUser);
        return newUser;
    }

    // search user
    public static ArrayList<User> searchUser(String username){
        ArrayList<User> result = new ArrayList<>();
        for (User user : userDataBase){
            if (user.getUsername().equals(username)){
                result.add(user);
            }
        }
        return result;
    }

    // share post
    public void UserSharePost(User user, User userDest, Post post){
        userDest.getSharedPosts().add(post);
    }

}
