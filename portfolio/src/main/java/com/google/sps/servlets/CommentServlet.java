package com.google.sps.servlets;

import java.util.ArrayList;
import java.util.List;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.gson.Gson;
import com.google.sps.data.Comment;
import com.google.sps.data.Conversation;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/comments")
public class CommentServlet extends HttpServlet {
  private Conversation conversation = new Conversation();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query("Comment").addSort("timestamp", SortDirection.DESCENDING);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    List<Comment> commentList = new ArrayList<>(); 
    for (Entity entity: results.asIterable()) {
        String username = (String) entity.getProperty("username");
        String commentText = (String) entity.getProperty("commentText");
        commentList.add(new Comment(username, commentText));
        System.out.println(commentList);
    }
    
    response.setContentType("application/json");
    String json = new Gson().toJson(commentList);
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Entity commentEntity = new Entity("Comment");
    commentEntity.setProperty("username", getUsername(request));
    commentEntity.setProperty("commentText", getComment(request));
    commentEntity.setProperty("timestamp", System.currentTimeMillis());
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    datastore.put(commentEntity);

    Comment comment = new Comment(getUsername(request), getComment(request));
    conversation.addComment(comment);

    response.sendRedirect("/community.html");
  }

  private String getUsername(HttpServletRequest request) {
    String username = request.getParameter("username");

    //TODO: Sanatize input here
    return username;
  }

  private String getComment(HttpServletRequest request) {
    String comment = request.getParameter("comment");

    //TODO: Sanatize input here
    return comment;
  }
}
