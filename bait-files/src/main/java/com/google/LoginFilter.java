package com.google;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import static com.google.sps.data.Keys.USER_ENTITY;
import static com.google.sps.data.Keys.ID_ENTITY_PROPERTY;
import java.net.URLEncoder;
import javax.servlet.RequestDispatcher;

@WebFilter("/")
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
        // If you have any <init-param> in web.xml, then you could get them
        // here by config.getInitParameter("name") and assign it as field.
    }



    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        UserService userService = UserServiceFactory.getUserService();

        //Case: User is not logged in
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
        //Case: User is logged in
        else {
            System.out.println("Hello this is the one");
            if (!isRegistered(userService.getCurrentUser().getUserId())) {
                //User is submitting form for registration
                if (request.getRequestURI().endsWith("register")) {
                    System.out.println("Nah I'm trying to access this");
                    chain.doFilter(req, res);
                    return;
                }
                //User is not registered and is trying to access restricted material
                else {
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/register.jsp");
                    requestDispatcher.forward(request, response);
                    return;
                }
            }
         }

         //Is logged in and registered but is trying to access profile jsp or now restricted register page
         if (request.getRequestURI().endsWith("profile.jsp") || request.getRequestURI().endsWith("register.jsp") || request.getRequestURI().endsWith("login.jsp")) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/profile.jsp");
            requestDispatcher.forward(request, response);
            return;
         }

         //Case: User is logged in and registered and wants to access site resource
         chain.doFilter(req, res);
         return;
    }

    public boolean isRegistered(String id) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query(USER_ENTITY)
          .setFilter(new Query.FilterPredicate(
            ID_ENTITY_PROPERTY, Query.FilterOperator.EQUAL, id));
        PreparedQuery results = datastore.prepare(query);
        Entity entity = results.asSingleEntity();
        return entity!=null;
    }

    @Override
    public void destroy() {
        // If you have assigned any expensive resources as field of
        // this Filter class, then you could clean/close them here.
    }

}