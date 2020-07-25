package com.reddit.Modules.SubredditManagement;

import com.reddit.Modules.UserManagement.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Subreddit {
    private User user;
    private static String subredditName;
    private static String subredditDescription;
    private static ArrayList<Post> subredditPosts = new ArrayList<>();
    private static ArrayList<Interaction> interactions = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public Subreddit(User user, String subredditName, String subredditDescription) {
        this.user = user;
        this.subredditName = subredditName;
        this.subredditDescription = subredditDescription;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getSubredditName() {
        return subredditName;
    }
    public void setSubredditName(String subredditName) {
        this.subredditName = subredditName;
    }
    public String getSubredditDescription() {
        return subredditDescription;
    }
    public void setSubredditDescription(String subredditDescription) {
        this.subredditDescription = subredditDescription;
    }
    public ArrayList<Post> getSubredditPosts() {
        return subredditPosts;
    }
    public void setSubredditPosts(ArrayList<Post> subredditPosts) {
        this.subredditPosts = subredditPosts;
    }
    public ArrayList<Interaction> getInteractions() {
        return interactions;
    }
    public void setInteractions(ArrayList<Interaction> interactions) {
        this.interactions = interactions;
    }

    // create subreddit
    public static Subreddit createSubreddit(User user, String subredditName, String subredditDescription){
        return new Subreddit(user, subredditName, subredditDescription);
    }

    // share subreddit
    public static Subreddit shareSubreddit(User user){
        System.out.print("Subreddit Name: ");
        subredditName = scanner.nextLine();
        System.out.print("Subreddit Description: ");
        subredditDescription = scanner.nextLine();
        Subreddit newSubreddit = createSubreddit(user, subredditName, subredditDescription);
        user.getUserSubreddits().add(newSubreddit);
//        System.out.println("size = " + user.getUserSubreddits().size());
//        showSubreddit(newSubreddit);
        return newSubreddit;
    }

    // delete subreddit
    public void deleteSubreddit(User user, Subreddit subreddit){
//        for (Subreddit subreddit1 : user.getUserSubreddits()){
//            if (subreddit == subreddit1){
//                user.getUserSubreddits().remove(subreddit);
//            }
//        }
        user.getUserSubreddits().remove(subreddit);
        System.out.println("Done!");
    }

    // Add Interaction
    public static void addInteraction(Interaction interaction){
        interactions.add(interaction);
    }

    // show subreddit detail
    public static void showSubredditDetail(Subreddit subreddit){
        System.out.println("\t" + countScore() + " Score");
        System.out.println("\tCreator: " + subreddit.getUser().getUsername());
        System.out.println("\tSubreddit Name: " + subreddit.getSubredditName());
        System.out.println("\tSubreddit Description: " + subreddit.getSubredditDescription());
    }

    // show subreddit
    public static void showSubreddit(Subreddit subreddit){
        int count = 1;
        System.out.println("\t" + countScore() + " Score");
        System.out.println("\tCreator: " + subreddit.getUser().getUsername());
        System.out.println("\tSubreddit Name: " + subreddit.getSubredditName());
        System.out.println("\tSubreddit Description: " + subreddit.getSubredditDescription());
        System.out.println();
        for (Post post : subredditPosts){
            System.out.print(count + ".");
            post.showPostDetail(post);
            System.out.println();
        }
    }

    // most popular subreddit posts
    public static ArrayList<Post> popularSubredditPost(User user, Subreddit subreddit){
        ArrayList<Post> popularPosts = new ArrayList<>();
        popularPosts.addAll(subreddit.getSubredditPosts());
        popularPosts.sort(new Comparator<Post>() {
            @Override
            public int compare(Post post1, Post post2) {
                if (post1.countScore() > post2.countScore()){
                    return 1;
                }
                if (post1.countScore() < post2.countScore()){
                    return -1;
                }
                return 0;
            }
        });
        if (popularPosts.size() <=3){
            return popularPosts;
        }
        else {
            ArrayList newPopularPosts = new ArrayList();
            for (int i=0; i<3; i++){
                newPopularPosts.add(popularPosts.get(i));
            }
            return newPopularPosts;
        }
    }

    // show popular subreddit posts
    public static Post showPopularSubredditPosts(User user){
        int count = 1;
        ArrayList<Subreddit> subreddits = user.getUserRelations();
        ArrayList<Post> popularSubredditPosts = new ArrayList<>();
        ArrayList<Post> allPosts = new ArrayList<>();
        for (Subreddit subreddit : subreddits) {
            System.out.println(subreddit.getSubredditName() + " : ");
            popularSubredditPosts = popularSubredditPost(user, subreddit);
            for (Post post : popularSubredditPosts) {
                allPosts.addAll(popularSubredditPosts);
                System.out.println(count + ". ");
                post.showPostDetail(post);
                ++count;
            }
        }
        if (count == 1){
            return null;
        }
        else {
            System.out.print("Choose a post: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            while (choice > count || choice == 0) {
                System.out.print("Out of range! Choose again: ");
                choice = scanner.nextInt();
                scanner.nextLine();
            }
            Post p = allPosts.get(choice - 1);
            p.showPost(p);

            return p;
        }
    }

    // search subreddit
    public static ArrayList<Subreddit> searchSubreddit(String subredditNameWord){
        ArrayList<Subreddit> result = new ArrayList<>();
        for (User user : User.userDataBase){
            for (Subreddit subreddit : user.getUserSubreddits()){
                if (subreddit.getSubredditName().contains(subredditNameWord)){
                    result.add(subreddit);
                }
            }
        }
        return result;
    }

    // count score
    public static int countScore(){
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

}
