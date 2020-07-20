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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for determining if a username has already been stored in datastore
 */
@WebServlet("/usernameTaken")
public class UsernameTakenServlet extends HttpServlet {
  /**
   * Adds a new user with form submitted fields to database
   * @param request contains a url parameter with username to check
   * @param response returns a json response with a boolean value if username is taken
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String username = request.getParameter("username");

    response.setContentType("application/json");
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    if (username == null || username.equals("")) {
      String json = new Gson().toJson(true);
      response.getWriter().println(json);
      return;
    }

    Query query = new Query("User").setFilter(
        new Query.FilterPredicate("username", Query.FilterOperator.EQUAL, username));
    PreparedQuery results = datastore.prepare(query);
    Entity entity = results.asSingleEntity();
    boolean usernameTaken = entity != null;

    response.setContentType("application/json");
    String json = new Gson().toJson(usernameTaken);
    response.getWriter().println(json);
  }
}