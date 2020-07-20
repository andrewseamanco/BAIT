package com.google;

import static java.util.stream.Collectors.toList;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.sps.servlets.User;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
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
    else if (!userIsRegistered(userService.getCurrentUser().getUserId())
        && !request.getRequestURI().startsWith("/_ah/")) {
      // Sending a request to a register servlet (disallows requests to html or jsp pages)
      if (!request.getRequestURI().endsWith("jsp") && !request.getRequestURI().endsWith("html")) {
        chain.doFilter(req, res);
        return;
      }
      // User is not registered and is trying to access restricted material
      else {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/register.jsp");
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

  private boolean userIsRegistered(String id) {
    List<User> allUsers = ObjectifyService.ofy().load().type(User.class).list();

    List<User> listOfUsersWithId =
        allUsers.stream().filter(user -> user.getUserId().equals(id)).collect(toList());

    return listOfUsersWithId.size() >= 1;
  }

  @Override
  public void destroy() {}
}