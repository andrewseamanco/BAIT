package com.google.sps.data;

public class Comment {
  private final String username;
  private final String commentText;

  public Comment(String username, String commentText) {
    this.username = username;
    this.commentText = commentText;
  }
}