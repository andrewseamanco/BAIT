package com.google;

import static java.util.stream.Collectors.toList;
import static com.google.common.collect.MoreCollectors.onlyElement;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.sps.servlets.User;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import com.google.sps.servlets.Enums.Permission;
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
    if (!userService.isUserLoggedIn() || !userIsRegistered(userService.getCurrentUser().getUserId())) {
      if (!request.getRequestURI().endsWith("jsp") && !request.getRequestURI().endsWith("html")) {
        chain.doFilter(req, res);
        return;
      } else {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/login.jsp");
        requestDispatcher.forward(request, response);
        return;
      }
    } else {
      // Case: User is logged in
      if (request.getRequestURI().endsWith("profile.jsp")
          || request.getRequestURI().endsWith("register.jsp")
          || request.getRequestURI().endsWith("login.jsp")) {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/profile.jsp");
        requestDispatcher.forward(request, response);
        return;
        //Case: User is trying to access ADMIN features of the application
      } else if (request.getRequestURI().endsWith("requests.html")) {
          if (getCurrentUserPermission() == Permission.ADMIN) {
              chain.doFilter(req, res);
          } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/profile.jsp");
            requestDispatcher.forward(request, response);  
          }
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


  /*
  *  Will get the permission of the current logged in User
  *  @return current user permission
  */
  private Permission getCurrentUserPermission() {
    List<User> allUsers = ObjectifyService.ofy().load().type(User.class).list();
    return allUsers.stream()
      .filter(user -> 
        user.getUserId().equals(UserServiceFactory.getUserService().getCurrentUser().getUserId()))
      .collect(onlyElement())
      .getPermission();
  }

  @Override
  public void destroy() {}
}