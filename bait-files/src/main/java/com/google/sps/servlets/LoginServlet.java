package com.google.sps.servlets;

import static com.google.sps.data.Keys.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class LoginServlet extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

      UserService userService = UserServiceFactory.getUserService();
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

      Entity newUser = new Entity(USER_ENTITY, userService.getCurrentUser().getUserId());
      newUser.setProperty(ID_ENTITY_PROPERTY, userService.getCurrentUser().getUserId());
      newUser.setProperty(USERNAME_ENTITY_PROPERTY, request.getParameter(USERNAME_ENTITY_PROPERTY));
      newUser.setProperty(
          FIRST_NAME_ENTITY_PROPERTY, request.getParameter(FIRST_NAME_ENTITY_PROPERTY));
      newUser.setProperty(
          LAST_NAME_ENTITY_PROPERTY, request.getParameter(LAST_NAME_ENTITY_PROPERTY));
      newUser.setProperty(IS_ADMIN_ENTITY_PROPERTY, false);

      datastore.put(newUser);

    RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/profile.jsp");
    requestDispatcher.forward(request, response);
  }


}
