package com.google.sps.servlets;

import static com.google.sps.data.Keys.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class LoginServlet extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    PrintWriter out = response.getWriter();

    boolean filledOutUsername = request.getParameter(USERNAME_ENTITY_PROPERTY) != null;
    boolean filledOutFirstName = request.getParameter(FIRST_NAME_ENTITY_PROPERTY) != null;
    boolean filledOutLastName = request.getParameter(LAST_NAME_ENTITY_PROPERTY) != null;

    if (usernameTaken(request.getParameter(USERNAME_ENTITY_PROPERTY))) {
      response.setContentType("text/html");
      out.println("Username is already taken.  Please try another username");
      RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/register.jsp");
      requestDispatcher.forward(request, response);
      return;
    } else if (!filledOutLastName || !filledOutFirstName || !filledOutUsername) {
      response.setContentType("text/html");
      out.println("Please fill out all fields before submitting");
      RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/register.jsp");
      requestDispatcher.forward(request, response);
      return;
    } else {
      UserService userService = UserServiceFactory.getUserService();
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

      Entity newUser = new Entity(USER_ENTITY, userService.getCurrentUser().getUserId());
      newUser.setProperty(ID_ENTITY_PROPERTY, userService.getCurrentUser().getUserId());
      newUser.setProperty(USERNAME_ENTITY_PROPERTY, request.getParameter(USERNAME_ENTITY_PROPERTY));
      newUser.setProperty(
          FIRST_NAME_ENTITY_PROPERTY, request.getParameter(FIRST_NAME_ENTITY_PROPERTY));
      newUser.setProperty(
          LAST_NAME_ENTITY_PROPERTY, request.getParameter(LAST_NAME_ENTITY_PROPERTY));
      newUser.setProperty(IS_ADMIN_ENTITY_PROPERTY, false);
    }

    RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/profile.jsp");
    requestDispatcher.forward(request, response);
  }

  public boolean usernameTaken(String username) {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Query query = new Query(USER_ENTITY)
                      .setFilter(new Query.FilterPredicate(
                          USERNAME_ENTITY_PROPERTY, Query.FilterOperator.EQUAL, username));
    PreparedQuery results = datastore.prepare(query);
    Entity entity = results.asSingleEntity();
    return entity != null;
  }
}
