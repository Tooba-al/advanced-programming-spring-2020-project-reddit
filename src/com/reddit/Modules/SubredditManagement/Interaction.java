package com.reddit.Modules.SubredditManagement;

import com.reddit.Modules.UserManagement.User;

import java.util.ArrayList;

abstract public class Interaction {
    private User user;
    private static ArrayList<Interaction> interactionInteractions = new ArrayList<>();

    public Interaction(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Interaction> getInteractionInteractions() {
        return interactionInteractions;
    }

    public void setInteractionInteractions(ArrayList<Interaction> interactionInteractions) {
        this.interactionInteractions = interactionInteractions;
    }

    // Add Interaction
    public static void addInteraction(Interaction interaction){
        interactionInteractions.add(interaction);
    }
}
