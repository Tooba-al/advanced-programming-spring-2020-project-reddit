package com.reddit.Modules.SubredditManagement;

import com.reddit.Modules.UserManagement.User;

public class Score extends Interaction{
    public Score(User user) {
        super(user);
    }

    // upvote comment
    public static Score upvoteSubreddit(User user, Subreddit subreddit){
        Score score = new Score(user);
        Subreddit.addInteraction(score);
        return score;
    }

    // downvote comment
    public static void downvoteSubreddit(User user, Subreddit subreddit){
        for (Interaction interaction : subreddit.getInteractions()){
            if (interaction instanceof Score){
                if (interaction.getUser().getUsername().equals(user.getUsername())) {
                    subreddit.getInteractions().remove(interaction);
                    return;
                }
            }
        }
    }

    // upvote post
    public static Score upvotePost(User user, Post post){
        Score score = new Score(user);
        post.addInteraction(score);
        return score;
    }

    // downvote post
    public static void downvotePost(User user, Post post){
        for (Interaction interaction : post.getInteractions()){
            if (interaction instanceof Score){
                if (interaction.getUser().getUsername().equals(user.getUsername())) {
                    post.getInteractions().remove(interaction);
                    return;
                }
            }
        }
    }

    // upvote comment
    public static Score upvoteComment(User user, Comment comment){
        Score score = new Score(user);
        comment.addInteraction(score);
//        comment.getScores().add(score);
        return score;
    }

    // downvote comment
    public static void downvoteComment(User user, Comment comment){
        for (Interaction interaction : comment.getInteractions()){
            if (interaction instanceof Score){
                if (interaction.getUser().getUsername().equals(user.getUsername())) {
                    comment.getInteractions().remove(interaction);
                    return;
                }
            }
        }
    }
}
