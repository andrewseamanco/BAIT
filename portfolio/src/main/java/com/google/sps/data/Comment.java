package com.google.sps.data;

public class Comment {
    private String username;
    private String commentText;
    private boolean isAdmin;

    public Comment (String username, String commentText, boolean isAdmin) {
        this.username = username;
        this.commentText = commentText;
        this.isAdmin = isAdmin;
    }
}