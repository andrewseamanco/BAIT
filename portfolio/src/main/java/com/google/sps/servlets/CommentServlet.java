package com.google.sps.servlets;

import static java.util.stream.Collectors.toList;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import com.google.sps.data.Comment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/comments")
public class CommentServlet extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Entity commentEntity = new Entity("Comment");
    commentEntity.setProperty("username", getUsername(request));
    commentEntity.setProperty("commentText", getComment(request));
    commentEntity.setProperty("timestamp", System.currentTimeMillis());
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    datastore.put(commentEntity);

    response.sendRedirect("/community.html");
  }

  private String getUsername(HttpServletRequest request) {
    String username = request.getParameter("username");

    // TODO: Sanatize input here
    return username;
  }

  private String getComment(HttpServletRequest request) {
    String comment = request.getParameter("comment");

    // TODO: Sanatize input here
    return comment;
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String requestedNumString = request.getParameter("commentRequestedNum");
    int requestedNumInt = Integer.parseInt(requestedNumString);

    Query query = new Query("Comment").addSort("timestamp", SortDirection.DESCENDING);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    List<Entity> commentEntityList =
        results.asList(FetchOptions.Builder.withLimit(requestedNumInt));

    List<Comment> commentList =
        commentEntityList.stream()
            .map(entity
                -> new Comment((String) entity.getProperty("username"),
                    (String) entity.getProperty("commentText"), entity.getKey().getId()))
            .collect(toList());

    response.setContentType("application/json");
    String json = new Gson().toJson(commentList);
    response.getWriter().println(json);
  }
}
