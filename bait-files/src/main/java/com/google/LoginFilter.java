package com.google;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyService;
import com.google.sps.servlets.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import static java.util.stream.Collectors.toList;
import com.googlecode.objectify.cmd.Query;

/**
 * Filter that disallows users to access site resources unless logged in and registered
 */
@WebFilter("/")
public class LoginFilter implements Filter {
  @Override
  public void init(FilterConfig config) throws ServletException {}

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;
    HttpSession session = request.getSession(false);
    UserService userService = UserServiceFactory.getUserService();

    // Case: User is not logged in
    if (!userService.isUserLoggedIn()) {
      if (request.getRequestURI().endsWith("login")) {
        chain.doFilter(req, res);
        return;
      } else {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/login.jsp");
        requestDispatcher.forward(request, response);
        return;
      }
    }
    // Case: User is logged in
    else if (!isRegistered(userService.getCurrentUser().getUserId()) && !request.getRequestURI().startsWith("/_ah/")) {
        // Sending a request to a register servlet (disallows requests to html or jsp pages)
        if (!request.getRequestURI().endsWith("jsp") && !request.getRequestURI().endsWith("html")) {
          chain.doFilter(req, res);
          return;
        }
        // User is not registered and is trying to access restricted material
        else {
          RequestDispatcher requestDispatcher =
          request.getRequestDispatcher("WEB-INF/register.jsp");
          requestDispatcher.forward(request, response);
          return;
        }
      } else {
      // Is logged in
      if (request.getRequestURI().endsWith("profile.jsp")
        || request.getRequestURI().endsWith("register.jsp")
        || request.getRequestURI().endsWith("login.jsp")) {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/profile.jsp");
        requestDispatcher.forward(request, response);
        return;
      } else {
        // Case: User is logged in and registered and wants to access site resource
        chain.doFilter(req, res);
        return;
      }
    }
}

  public boolean isRegistered(String id) {
    Query<User> userQuery = ObjectifyService.ofy().load().type(User.class);
    List<User> allUsers = userQuery.list();

    List<User> isRegistered =
        allUsers.stream().filter(user -> user.getUserId().equals(id)).collect(toList());    

    return isRegistered.size() >= 1;
  }

  @Override
  public void destroy() {}
}