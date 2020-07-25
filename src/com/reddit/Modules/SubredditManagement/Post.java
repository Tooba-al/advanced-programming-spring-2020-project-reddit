package com.reddit.Modules.SubredditManagement;

import com.reddit.Modules.UserManagement.User;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Post {
    private Subreddit subreddit;
    private User user;
    private static String postTitle;
    private static String postContent;
    private ArrayList<Interaction> interactions = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public Post(User user, Subreddit subreddit, String postTitle, String postContent) {
        this.user = user;
        this.subreddit = subreddit;
        this.postTitle = postTitle;
        this.postContent = postContent;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Subreddit getSubreddit() {
        return subreddit;
    }
    public void setSubreddit(Subreddit subreddit) {
        this.subreddit = subreddit;
    }
    public String getPostTitle() {
        return postTitle;
    }
    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }
    public String getPostContent() {
        return postContent;
    }
    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }
    public ArrayList<Interaction> getInteractions() {
        return interactions;
    }
    public void setInteractions(ArrayList<Interaction> interactions) {
        this.interactions = interactions;
    }

    // create post
    public static Post createPost(User user, Subreddit subreddit, String postTitle, String postContent){
        return new Post(user, subreddit, postTitle, postContent);
    }

    // Add Interaction
    public void addInteraction(Interaction interaction){
        interactions.add(interaction);
    }

    // share post
    public static Post sharePost(User user, Subreddit subreddit){
        System.out.print("Title: ");
        postTitle = scanner.nextLine();
        System.out.print("Content: ");
        postContent = scanner.nextLine();
        Post newPost = createPost(user, subreddit, postTitle, postContent);
        subreddit.getSubredditPosts().add(newPost);
//        showPost(newPost);
        return newPost;
    }

    // show post
    public static void showPost(Post post){
//        System.out.println("Topic Name: " + );
        Subreddit postSubreddit = post.getSubreddit();
        Subreddit.showSubredditDetail(postSubreddit);
        System.out.println();
        System.out.println("\tAuthor: " + post.getUser().getUsername());
        System.out.println("\tTitle: " + post.getPostTitle());
        System.out.println("\tContent: " + post.getPostContent());
        System.out.println("\t" + post.countScore() + " Score " + post.countComment() + " Comment");
    }

    // show post detail
    public void showPostDetail(Post post){
        System.out.println("\tAuthor: " + post.getUser().getUsername());
        System.out.println("\tTitle: " + post.getPostTitle());
        System.out.println("\t" + this.countScore() + " Score " + this.countComment() + " Comment");
    }

    // count score
    public int countScore(){
        int count = 0;
        for (Interaction interaction : interactions){
            if (interaction instanceof Score){
                ++count;
            }
        }
        return count;
    }

    // count comment
    public int countComment(){
        int count = 0;
        for (Interaction interaction : interactions){
            if (interaction instanceof Comment){
                ++count;
            }
        }
        return count;
    }

    // search post
    public static ArrayList<Post> searchPost(String postTitleWord){
        ArrayList<Post> result = new ArrayList<>();
        for (User user : User.userDataBase){
            for (Subreddit subreddit : user.getUserSubreddits()){
                for (Post post : subreddit.getSubredditPosts()){
                    if (post.getPostTitle().contains(postTitleWord)){
                        result.add(post);
                    }
                }
            }
        }
        return result;
    }

}
