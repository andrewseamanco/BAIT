package com.google.sps.servlets;

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


/**
 * Servlet for creating new users and storing them in the database
 */
@WebServlet("/register")
public class LoginServlet extends HttpServlet {


  /**
   * Adds a new user with form submitted fields to database
   * @param request contains form fields and used to instantiate new user fields
   * @param response redirects the page to profile after servlet has finished adding user
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    UserService userService = UserServiceFactory.getUserService();
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    Entity newUser = new Entity("User", request.getParameter("username"));
    newUser.setProperty("id", userService.getCurrentUser().getUserId());
    newUser.setProperty("username", request.getParameter("username"));
    newUser.setProperty("first-name", request.getParameter("first-name"));
    newUser.setProperty("last-name", request.getParameter("last-name"));
    newUser.setProperty("is-admin", false);

    datastore.put(newUser);

    response.sendRedirect("/profile.jsp");  
  }

}
