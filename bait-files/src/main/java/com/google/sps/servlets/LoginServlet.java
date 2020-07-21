package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
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
    private final UserAccessor userAccessor;

    public LoginServlet(UserAccessor userAccessor) {
        this.userAccessor = userAccessor;
    }

  /**
   * Adds a new user with form submitted fields to database
   * @param request contains form fields and used to instantiate new user fields
   * @param response redirects the page to profile after servlet has finished adding user
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    String userId = userAccessor.getUserId();
    String username = request.getParameter("username");
    String firstName = request.getParameter("first-name");
    String lastName = request.getParameter("last-name");
    User newUser = new User(userId, username, firstName, lastName);

    ObjectifyService.ofy().save().entities(newUser).now();

    response.sendRedirect("/profile.jsp");
  }
}
