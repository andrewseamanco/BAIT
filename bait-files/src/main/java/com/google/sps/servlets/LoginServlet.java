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

    Entity newUser = new Entity(USER_ENTITY, request.getParameter(USERNAME_ENTITY_PROPERTY));
    newUser.setProperty(ID_ENTITY_PROPERTY, userService.getCurrentUser().getUserId());
    newUser.setProperty(USERNAME_ENTITY_PROPERTY, request.getParameter(USERNAME_ENTITY_PROPERTY));
    newUser.setProperty(FIRST_NAME_ENTITY_PROPERTY, request.getParameter(FIRST_NAME_ENTITY_PROPERTY));
    newUser.setProperty(LAST_NAME_ENTITY_PROPERTY, request.getParameter(LAST_NAME_ENTITY_PROPERTY));
    newUser.setProperty(IS_ADMIN_ENTITY_PROPERTY, false);

    datastore.put(newUser);

    response.sendRedirect("/profile.jsp");  
  }

}
