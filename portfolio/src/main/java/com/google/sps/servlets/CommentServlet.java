package com.google.sps.servlets;

import static java.util.stream.Collectors.toList;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import com.google.sps.data.Comment;
import static com.google.sps.data.Keys.*;
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
      response.sendRedirect("/login");
      return;
    }

    Entity commentEntity = new Entity(COMMENT_ENTITY);
    commentEntity.setProperty(USERNAME_ENTITY_PROPERTY, getUsername(userService.getCurrentUser().getUserId()));
    commentEntity.setProperty(COMMENT_TEXT_ENTITY_PROPERTY, request.getParameter(COMMENT_TEXT_ENTITY_PROPERTY));
    commentEntity.setProperty(TIMESTAMP_ENTITY_PROPERTY, System.currentTimeMillis());
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    datastore.put(commentEntity);

    response.sendRedirect("/community.html");
  }

    private String getUsername(String id) {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Query query = new Query(USER_ENTITY).setFilter(
        new Query.FilterPredicate(ID_ENTITY_PROPERTY, Query.FilterOperator.EQUAL, id));
    PreparedQuery results = datastore.prepare(query);
    Entity entity = results.asSingleEntity();

    return entity == null ? getRandomUsername() : (String) entity.getProperty(USERNAME_ENTITY_PROPERTY);
  }

  private String getRandomUsername() {
      String[] adjectives = {"Fantastic", "Random", "Honorable", "Jaded", "Fake", "Haunted", "Fighting", "Nappy", "Feirce", "Jealous", "Shark"};
      String[] nouns = {"Goldfish", "Keyboard", "Pollution", "Bathroom", "Vehicle", "Week", "Republic", "Knowledge", "Economics"};
      return adjectives[(int)(Math.random()*adjectives.length)] + "_" + nouns[(int)(Math.random()*nouns.length)];
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    int requestedNumInt = Integer.parseInt(request.getParameter(COMMENT_NUM_REQUESTED));

    Query query = new Query(COMMENT_ENTITY).addSort(TIMESTAMP_ENTITY_PROPERTY, SortDirection.DESCENDING);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    List<Entity> commentEntityList =
        results.asList(FetchOptions.Builder.withLimit(requestedNumInt));

    List<Comment> commentList =
        commentEntityList.stream()
            .map(entity
                -> new Comment((String) entity.getProperty(USERNAME_ENTITY_PROPERTY),
                    (String) entity.getProperty(COMMENT_TEXT_ENTITY_PROPERTY), entity.getKey().getId()))
            .collect(toList());

    response.setContentType("application/json");
    String json = new Gson().toJson(commentList);
    response.getWriter().println(json);
  }

  @Override
  public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
      Query query = new Query(COMMENT_ENTITY);

      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      PreparedQuery results = datastore.prepare(query);

      List<Key> commentKeys = new ArrayList<>();
      for (Entity entity : results.asIterable()) {
        long id = entity.getKey().getId();
        commentKeys.add(KeyFactory.createKey(COMMENT_ENTITY, id));
      }

      datastore.delete(commentKeys);
  }
}


