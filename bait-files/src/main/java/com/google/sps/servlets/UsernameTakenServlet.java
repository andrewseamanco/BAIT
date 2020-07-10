package com.google.sps.servlets;

import static com.google.sps.data.Keys.*;
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
import java.util.List;
import java.util.Arrays;
import java.util.Random;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/usernameTaken")
public class UsernameTakenServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String username = request.getParameter("username");

    response.setContentType("application/json");
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    if (username.equals("") || username==null) {
        String json = new Gson().toJson(true);
        response.getWriter().println(json);
        return;
    }

    Query query = new Query(USER_ENTITY)
      .setFilter(new Query.FilterPredicate(
        USERNAME_ENTITY_PROPERTY, Query.FilterOperator.EQUAL, username));
    PreparedQuery results = datastore.prepare(query);
    Entity entity = results.asSingleEntity();
    boolean usernameTaken = entity!=null; 


    response.setContentType("application/json");
    String json = new Gson().toJson(usernameTaken);
    response.getWriter().println(json);
  }
}