package com.pruebas.tabs.pruebatabs.Model;

/**
 * Created by Kevin on 02/05/2016.
 */
public class Comentary {
    private User userCommentary;
    private String Comment;

    public Comentary(User userCommentary, String comment) {
        this.userCommentary = userCommentary;
        Comment = comment;
    }

    public User getUserCommentary() {
        return userCommentary;
    }

    public void setUserCommentary(User userCommentary) {
        this.userCommentary = userCommentary;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
