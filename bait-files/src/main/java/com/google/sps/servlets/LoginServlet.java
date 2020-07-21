package com.google.sps.servlets;

import static com.google.sps.data.Constants.*;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import com.google.sps.servlets.Enums.Permission;
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

    String userId = userService.getCurrentUser().getUserId();
    String username = request.getParameter(USERNAME_PARAMETER);
    String firstName = request.getParameter(FIRST_NAME_PARAMETER);
    String lastName = request.getParameter(LAST_NAME_PARAMETER);
    Permission userPermission = Permission.USER;

    User newUser = new User(userId, username, firstName, lastName, userPermission);

    ObjectifyService.ofy().save().entities(newUser).now();

    response.sendRedirect("/profile.jsp");
  }
}
