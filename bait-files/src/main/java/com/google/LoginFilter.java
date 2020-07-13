package com.google;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import java.net.URLEncoder;
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
import java.io.PrintWriter;


@WebFilter("/")
public class LoginFilter implements Filter {
  @Override
  public void init(FilterConfig config) throws ServletException {
  }

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
    
    // Case: User is logged in but not registered (will replace else if with code checking registered)

        /* TODO: Add case when user is not registered */

    else {
    
        // Is logged in and registered but is trying to access profile jsp or now restricted register
        // page
        if (request.getRequestURI().endsWith("profile.jsp")
            || request.getRequestURI().endsWith("register.jsp")
            || request.getRequestURI().endsWith("login.jsp")) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/login.jsp");
            requestDispatcher.forward(request, response);
            return;
    } else {
        // Case: User is logged in and registered and wants to access site resource
        chain.doFilter(req, res);
        return;
    }
  }
}

  @Override
  public void destroy() {
  }
}