package com.reddit.Pages;

import com.reddit.Modules.SubredditManagement.*;
import com.reddit.Modules.UserManagement.User;

import javax.print.attribute.standard.PDLOverrideSupported;
import java.sql.PseudoColumnUsage;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class PostPage {
    private User currentUser;
    private Subreddit subreddit;
    private Post post;
    private Comment comment;
    private Interaction interaction;

    public PostPage(User currentUser, Subreddit subreddit, Post post) {
        this.currentUser = currentUser;
        this.subreddit = subreddit;
        this.post = post;
    }
    public User getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    public Post getPost() {
        return post;
    }
    public void setPost(Post post) {
        this.post = post;
    }
    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
    public Interaction getInteraction() {
        return interaction;
    }
    public void setInteraction(Interaction interaction) {
        this.interaction = interaction;
    }

    // Creat post Page
    public static PostPage createPostPage(User user, Subreddit subreddit, Post post){
        return new PostPage(user, subreddit, post);
    }

    // upvote post
    private void upvotePost(){
        Score.upvotePost(currentUser, post);
        run();
    }

    // downvote post
    private void downvotePost(){
        Score.downvotePost(currentUser, post);
        run();
    }

    // upvote comment
    private void upvoteComment(){
        Score.upvoteComment(currentUser, comment);
        run();
    }

    // downvote comment
    private void downvoteComment(){
        Score.downvoteComment(currentUser, comment);
        run();
    }

    // Comment
    public void comment(){
        System.out.print("Comment Text: ");
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        Comment.comment(currentUser, post, text);
        run();
    }

    // Render
    public void run(){
        //Clean Console
        System.out.println("\033[H\033[2J");
        System.out.flush();
        System.out.println("     [Post Page]    ");
        System.out.println("--------------------");

        post.showPost(post);
        int count = 1;
        ArrayList<Comment> comments = new ArrayList<>();
        ArrayList<Interaction> interactions = new ArrayList<>();
        if (post.countComment() > 0) {
            System.out.println();
            System.out.println("Comments: ");
            for (Interaction interaction1 : post.getInteractions()) {
                if (interaction1 instanceof Comment) {
                    System.out.println(count + ".");
                    ((Comment) interaction1).showComment();
                    comments.add((Comment) interaction1);
                    interactions.add(interaction1);
                    System.out.println();
                    ++count;
                }
            }
        }
        else {
            System.out.println("\tNo Comments Yet!");
            System.out.println();
        }
        boolean hasVotedPost = false;
        boolean hasVotedComment = false;
        for (Interaction interaction : post.getInteractions()){
            if (interaction instanceof Score){
                if (interaction.getUser().getUsername().equals(currentUser.getUsername())){
                    hasVotedPost = true;
                    break;
                }
            }
        }
        for (Interaction interaction : comment.getInteractions()){
            if (interaction instanceof Score){
                if (interaction.getUser().getUsername().equals(currentUser.getUsername())){
                    hasVotedComment = true;
                    break;
                }
            }
        }
        if (!hasVotedPost){
            if (!hasVotedComment){
                System.out.println("1. Upvote Post\n2. Upvote Comment\n3. Insert a Comment\n4. Reply to comment\n5. Show reply comments\n6. Share this post to other users\n7. Back");
            }
            else {
                System.out.println("1. Upvote Post\n2. Downvote Comment\n3. Insert a Comment\n4. Reply to comment\n5. Show reply comments\n6. Share this post to other users\n7. Back");
            }
        }
        else {
            if (!hasVotedComment){
                System.out.println("1. Downvote Post\n2. Upvote Comment\n3. Insert a Comment\n4. Reply to comment\n5. Show reply comments\n6. Share this post to other users\n7. Back");
            }
            else {
                System.out.println("1. Downvote Post\n2. Downvote Comment\n3. Insert a Comment\n4. Reply to comment\n5. Show reply comments\n6. Share this post to other users\n7. Back");
            }
        }
        System.out.println();
        System.out.print("Choose a number: ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice){
            case 1:
                if (!hasVotedPost){
                    upvotePost();
                }
                else {
                    downvotePost();
                }
                run();
                break;
            case 2:
                if (count == 1){
                    System.out.println("No Comments Yet!");
                    System.out.println();
                    run();
                }
                else {
                    System.out.print("Choose a comment: ");
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    while (choice > count || choice == 0) {
                        System.out.print("Out of range! Choose again: ");
                        choice = scanner.nextInt();
                        scanner.nextLine();
                    }
                    Comment comment = comments.get(choice - 1);
                    Interaction interaction = comment;
                    if (!hasVotedComment) {
                        upvoteComment();
                    } else {
                        downvoteComment();
                    }
                    run();
                }
                break;
            case 3:
                comment();
                break;
            case 4:
                if (post.countComment() <= 0){
                    System.out.println("No Comments Yet!");
                    System.out.println();
                }
                else {
                    System.out.print("Choose a comment: ");
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    while (choice > count || choice == 0) {
                        System.out.print("Out of range! Choose again: ");
                        choice = scanner.nextInt();
                        scanner.nextLine();
                    }
                    comment = comments.get(choice - 1);
                    System.out.print("Reply comment: ");
                    String text = scanner.nextLine();
                    Comment newComment = Comment.comment(currentUser, post, text);
                    Comment.reply(currentUser, comment, text);
                }
                run();
                break;
            case 5:
                if (post.countComment() <= 0){
                    System.out.println("No Comments Yet!");
                    System.out.println();
                }
                else {
                    System.out.print("Choose a comment: ");
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    while (choice > count || choice == 0) {
                        System.out.print("Out of range! Choose again: ");
                        choice = scanner.nextInt();
                        scanner.nextLine();
                    }
                    count = 1;
                    comment = comments.get(choice - 1);
                    for (Comment reply : comment.getReplies()){
                        System.out.print(count + ". ");
                        reply.showComment();
                    }
//                    System.out.println("1. Upvote Comment\n3. Insert a Comment\n4. Reply to comment\n5. Show reply comments\n6. Share this post to other users\n7. Back to Home");
                }
                run();
                break;
            case 6:
                count = 1;
                for (User user : currentUser.getUserRelationUser()) {
                    System.out.println(count + ". " + user.getUsername());
                    ++count;
                }
                if (count == 1){
                    System.out.println("No Users Yet!");
                }
                else {
                    System.out.println();
                    System.out.print("Choose a number: ");
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    User shareUser = currentUser.getUserRelationUser().get(choice-1);
                    currentUser.UserSharePost(currentUser, shareUser, post);
                    System.out.println("Done!");
                }
                run();
                break;
            case 7:
                SubredditPage subredditPage = SubredditPage.createSubredditPage(currentUser, subreddit);
                subredditPage.run();
                break;
            default:
                run();
                break;
        }
    }



}
