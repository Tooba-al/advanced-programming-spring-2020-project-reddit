package com.reddit.Pages;

import com.reddit.Modules.SubredditManagement.*;
import com.reddit.Modules.UserManagement.User;

import java.util.Scanner;

public class SubredditPage {
    private User currentUser;
    private Subreddit subreddit;

    public SubredditPage(User currentUser, Subreddit subreddit) {
        this.currentUser = currentUser;
        this.subreddit = subreddit;
    }
    public User getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    public Subreddit getSubreddit() {
        return subreddit;
    }
    public void setSubreddit(Subreddit subreddit) {
        this.subreddit = subreddit;
    }

    // Creat Tweet Page
    public static SubredditPage createSubredditPage(User user, Subreddit subreddit){
        return new SubredditPage(user, subreddit);
    }

    // Score
    private void upVote(){
        Score.upvoteSubreddit(currentUser, subreddit);
        run();
    }

    // Dislike
    private void downVote(){
        Score.downvoteSubreddit(currentUser, subreddit);
        run();
    }

    // Render
    public void run() {
        //Clean Console
        System.out.println("\033[H\033[2J");
        System.out.flush();
        System.out.println("  [Subreddit Page]  ");
        System.out.println("--------------------");

        subreddit.showSubreddit(subreddit);
        boolean hasVoted = false;
        for (Interaction interaction : subreddit.getInteractions()) {
            if (interaction instanceof Score) {
                if (interaction.getUser().getUsername().equals(currentUser.getUsername())) {
                    hasVoted = true;
                    break;
                }
            }
        }
        boolean hasjoined = currentUser.hasJoined(currentUser, subreddit);
        if (subreddit.getUser() == currentUser){
            hasjoined = true;
        }
        if (hasjoined == false){
            if (!hasVoted) {
                System.out.println();
                System.out.println("1. Upvote\n2. Join Subreddit\n3. Show Posts\n4. Back to Home");
            } else {
                System.out.println();
                System.out.println("1. Downvote\n2. Join Subreddit\n3. Show Posts\n4. Back to Home");
            }
//            System.out.println();
            System.out.print("Choose a number: ");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice){
                case 1:
                    if (!hasVoted){
                        upVote();
                    }
                    else {
                        downVote();
                    }
                    run();
                    break;
                case 2:
                    currentUser.join(currentUser, subreddit);
                    run();
                    break;
                case 3:
                    int count = 1;
                    if (subreddit.getSubredditPosts() == null){
                        System.out.println("No Posts Yet!");
                        run();
                    }
                    else {
                        for (Post post : subreddit.getSubredditPosts()) {
                            System.out.println();
                            System.out.print(count + ". ");
                            post.showPostDetail(post);
                            ++count;
                        }
                        if (count == 1){
                            System.out.println("No Posts Yet!");
                            run();
                        }
                        else {
//                            System.out.println();
                            System.out.print("Choose a post to show detail: ");
                            choice = scanner.nextInt();
                            scanner.nextLine();
                            Post resultPost = subreddit.getSubredditPosts().get(choice - 1);
                            PostPage postPage = PostPage.createPostPage(currentUser, subreddit, resultPost);
                            postPage.run();
                        }
                    }
                    break;
                case 4:
                    HomePage homePage = HomePage.createHomePage(currentUser);
                    homePage.run();
                    break;
                default:
                    run();
                    break;
            }
        }
        else {
            boolean canDelete = false;
            for (Subreddit subreddit1 : currentUser.getUserSubreddits()){
                if (subreddit1 == subreddit){
                    canDelete = true;
                }
            }
            if (canDelete == true){
                if (!hasVoted) {
                    System.out.println("1. Upvote\n2. Share a post\n3. Show Posts\n4. Delete Subreddit\n5. Back to Home");
                }
                else {
                    System.out.println("1. Downvote\n2. Share a post\n3. Show Posts\n4. Delete Subreddit\n5. Back to Home");
                }
            }
            else {
                if (!hasVoted) {
                    System.out.println();
                    System.out.println("1. Upvote\n2. Share a post\n3. Show Posts\n4. Leave Subreddit\n5. Back to Home");
                } else {
                    System.out.println();
                    System.out.println("1. Downvote\n2. Share a post\n3. Show Posts\n4. Leave Subreddit\n5. Back to Home");
                }
            }
            System.out.println();
            System.out.print("Choose a number: ");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice){
                case 1:
                    if (!hasVoted){
                        upVote();
                    }
                    else {
                        downVote();
                    }
                    run();
                    break;
                case 2:
                    Post newPost = Post.sharePost(currentUser, subreddit);
                    PostPage postPage = PostPage.createPostPage(currentUser, subreddit, newPost);
                    postPage.run();
                    run();
                    break;
                case 3:
                    int count = 1;
                    if (subreddit.getSubredditPosts() == null){
                        System.out.println("No Posts Yet!");
                        run();
                    }
                    else {
                        for (Post post : subreddit.getSubredditPosts()) {
                            System.out.print(count + ". ");
                            post.showPostDetail(post);
                            System.out.println();
                            ++count;
                        }
                        if (count == 1){
                            System.out.println("No Posts Yet!");
                            run();
                        }
                        else {
//                            System.out.println();
                            System.out.print("Choose a post to show detail: ");
                            choice = scanner.nextInt();
                            scanner.nextLine();
                            Post resultPost = subreddit.getSubredditPosts().get(choice - 1);
                            postPage = PostPage.createPostPage(currentUser, subreddit, resultPost);
                            postPage.run();
                        }
                    }
                    break;
                case 4:
                    if (canDelete == true){
                        subreddit.deleteSubreddit(currentUser, subreddit);
                        HomePage homePage = HomePage.createHomePage(currentUser);
                        homePage.run();
                    }
                    else {
                        currentUser.leave(currentUser, subreddit);
                        hasjoined = false;
                        run();
                    }
                    break;
                case 5:
                    HomePage homePage = HomePage.createHomePage(currentUser);
                    homePage.run();
                    break;
                default:
                    run();
                    break;
            }
        }

    }
}
