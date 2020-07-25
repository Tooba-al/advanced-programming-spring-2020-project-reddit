package com.reddit.Modules.UserManagement;

public class UserRelation {
    private User user;

    public UserRelation(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
