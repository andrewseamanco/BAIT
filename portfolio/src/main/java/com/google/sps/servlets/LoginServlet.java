package com.google.sps.servlets;

import static com.google.sps.data.Keys.ID_ENTITY_PROPERTY;
import static com.google.sps.data.Keys.USERNAME_ENTITY_PROPERTY;
import static com.google.sps.data.Keys.USER_ENTITY;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("login")
public class LoginServlet extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    UserService userService = UserServiceFactory.getUserService();
    PrintWriter out = response.getWriter();

    if (!userService.isUserLoggedIn()) {
      String loginUrl = userService.createLoginURL("/login");
      out.println("<h2>Log In Or Sign Up!</h2>");
      out.println("<p>Login <a href=\"" + loginUrl + "\">here</a>.</p>");
    } else {
      String logoutUrl = userService.createLogoutURL("/");
      out.println("<p>Logout <a href=\"" + logoutUrl + "\">here</a>.</p>");
      out.println("<p>Username:");
      out.println("<form method=\"POST\" action=\"login\">");
      out.println("<input name=\"username\" >");
      out.println("<br />");
      out.println("<button>Submit</button>");
      out.println("</form>");
    }
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    UserService userService = UserServiceFactory.getUserService();

    if (!userService.isUserLoggedIn()) {
      response.sendRedirect("/login");
      return;
    }

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Entity newUser = new Entity(USER_ENTITY, userService.getCurrentUser().getUserId());
    newUser.setProperty(ID_ENTITY_PROPERTY, userService.getCurrentUser().getUserId());
    newUser.setProperty(USERNAME_ENTITY_PROPERTY, request.getParameter(USERNAME_ENTITY_PROPERTY));

    datastore.put(newUser);

    response.sendRedirect("/community.html");
  }
}
