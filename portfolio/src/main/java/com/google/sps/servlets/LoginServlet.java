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
      String loginUrl = userService.createLoginURL("/Login");
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
    String username = request.getParameter("username");
    String id = userService.getCurrentUser().getUserId();

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Entity newUser = new Entity("user", id);
    newUser.setProperty("id", id);
    newUser.setProperty("username", username);

    datastore.put(newUser);

    response.sendRedirect("/community.html");
  }
}
