package com.google.sps.servlets;

import static java.util.stream.Collectors.toList;

import com.googlecode.objectify.cmd.Query;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyService;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
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
    if (username == null || username.equals("")) {
        String json = new Gson().toJson(true);
        response.getWriter().println(json);
        return;
    }

    List<User> allUsers = ObjectifyService.ofy().load().type(User.class).list();

    List<User> usersWithUsername =
        allUsers.stream().filter(user -> user.username.equals(username)).collect(toList());        
        
    boolean usernameTaken = usersWithUsername.size() >= 1; 

    response.setContentType("application/json");
    String json = new Gson().toJson(usernameTaken);
    response.getWriter().println(json);
  }
}