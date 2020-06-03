package com.google.sps.data;

public class Comment {
  private final String username;
  private final String commentText;
  private final long id;

  public Comment(String username, String commentText, long id) {
    this.username = username;
    this.commentText = commentText;
    this.id = id;
  }
}