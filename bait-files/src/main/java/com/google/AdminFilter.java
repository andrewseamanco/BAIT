package com.google;

import static com.google.common.collect.MoreCollectors.onlyElement;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.sps.servlets.Enums.Permission;
import com.google.sps.servlets.User;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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

@WebFilter("admin/*")
public class AdminFilter implements Filter {
  @Override
  public void init(FilterConfig config) throws ServletException {}

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;
    HttpSession session = request.getSession(false);
    UserService userService = UserServiceFactory.getUserService();

    if (getCurrentUserPermission() == Permission.USER) {
      RequestDispatcher requestDispatcher = request.getRequestDispatcher("../WEB-INF/history.jsp");
      requestDispatcher.forward(request, response);
    } else {
      chain.doFilter(req, res);
      return;
    }
  }

  public Permission getCurrentUserPermission() {
    List<User> allUsers = ObjectifyService.ofy().load().type(User.class).list();
    return allUsers.stream()
        .filter(user
            -> user.getUserId().equals(
                UserServiceFactory.getUserService().getCurrentUser().getUserId()))
        .collect(onlyElement())
        .getPermission();
  }
}