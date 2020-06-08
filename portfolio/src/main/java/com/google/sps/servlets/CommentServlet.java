package com.google.sps.servlets;

import static java.util.stream.Collectors.toList;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.users.UserService;
import com.google.gson.Gson;
import com.google.sps.data.Comment;
import java.io.IOException;
import java.io.PrintWriter;
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
    UserService userService = UserServiceFactory.getUserService();
    PrintWriter out = response.getWriter();

    if (!userService.isUserLoggedIn()) {
        out.print("<p>Please login before posting a comment </p>");
        response.sendRedirect("/Login");
        return;
    }

    Entity commentEntity = new Entity("Comment");
    commentEntity.setProperty("username", getUsername(userService.getCurrentUser().getUserId()));
    commentEntity.setProperty("commentText", getComment(request));
    commentEntity.setProperty("timestamp", System.currentTimeMillis());
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    datastore.put(commentEntity);

    response.sendRedirect("/community.html");
  }

  private String getUsername(HttpServletRequest request) {
    return request.getParameter("username");
  }

  private String getComment(HttpServletRequest request) {
    return request.getParameter("comment");
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    int requestedNumInt = Integer.parseInt(request.getParameter("commentRequestedNum"));

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

    private String getUsername(String id) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query =
            new Query("user")
                .setFilter(new Query.FilterPredicate("id", Query.FilterOperator.EQUAL, id));
        PreparedQuery results = datastore.prepare(query);
        Entity entity = results.asSingleEntity();

        if (entity == null) {
            return null;
        }

        System.out.println("======NOT+NULLLLLLL=======");


        String nickname = (String) entity.getProperty("username");
        return nickname;
  }

  @Override
  public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
    boolean deleteAll = true;
    if (deleteAll) {
        Query query = new Query("Comment");

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery results = datastore.prepare(query);

    List<Key> commentKeys = new ArrayList<>();
        for (Entity entity : results.asIterable()) {
            long id = entity.getKey().getId();
            Key taskEntityKey = KeyFactory.createKey("Comment", id);
            commentKeys.add(taskEntityKey);
        }

        datastore.delete(commentKeys);
    }
}
}
