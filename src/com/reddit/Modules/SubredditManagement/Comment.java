package com.reddit.Modules.SubredditManagement;

import com.reddit.Modules.UserManagement.User;

import java.util.ArrayList;
import java.util.Scanner;

public class Comment extends Interaction{
    private String text;
    private static ArrayList<Interaction> interactions = new ArrayList<>();
    private static ArrayList<Comment> replies = new ArrayList<>();
    private static ArrayList<Score> scores = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public Comment(User user, String text) {
        super(user);
        this.text = text;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public static ArrayList<Interaction> getInteractions() {
        return interactions;
    }
    public static void setInteractions(ArrayList<Interaction> interactions) {
        Comment.interactions = interactions;
    }
    public static ArrayList<Comment> getReplies() {
        return replies;
    }
    public static void setReplies(ArrayList<Comment> replies) {
        Comment.replies = replies;
    }
    public static ArrayList<Score> getScores() {
        return scores;
    }
    public static void setScores(ArrayList<Score> scores) {
        Comment.scores = scores;
    }

    // Comment
    public static Comment comment(User user, Post post, String text){
        Comment comment = new Comment(user, text);
        post.addInteraction(comment);
        return comment;
    }

    // Show Comment
    public void showComment(){
        System.out.println("\t*" + getUser().getUsername() + "* : " + text);
        System.out.println("\t" + countScore() + " Score");
    }

    // Add Interaction
    public static void addInteraction(Interaction interaction){
        interactions.add(interaction);
    }

    // add reply
    public static void addReply(Comment comment){
        replies.add(comment);
    }

    // count score
    public static int countScore(){
//        int count = scores.size();
        int count = 0;
        for (Interaction interaction : interactions){
            if (interaction instanceof Score){
                ++count;
            }
        }
        return count;
    }

    // change string
    private static String changeString(String text, Comment comment){
        String newText = "[replied to " + comment.getUser().getUsername() + "] " + text;
        return newText;
    }

    // reply
    public static void reply(User user, Comment comment, String text){
//        String newText = "[replied to " + comment.getUser().getUsername() + "] " + text;
//        comment.setText(newText);
        String newText = changeString(text, comment);
//        text.replaceAll(text, newText);
        Comment newComment = new Comment(user, newText);
//        newComment.setText(newText);
//        System.out.println(newComment.getText());
//        comment.addInteraction(newComment);
        comment.addReply(newComment);
        comment.showComment();
        newComment.showComment();
    }

}
