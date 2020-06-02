package com.google.sps.data;

import java.util.ArrayList;
import java.util.List;

public class Conversation {
  private final List<Comment> commentList = new ArrayList<Comment>();

    public void addComment(Comment comment) {
        commentList.add(comment);
    }

}