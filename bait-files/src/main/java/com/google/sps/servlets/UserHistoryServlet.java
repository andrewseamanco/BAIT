package com.google.sps.servlets;

import static java.util.stream.Collectors.toList;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import com.google.sps.servlets.Enums.Status;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import java.io.IOException;
import java.lang.String;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet loads all pending requests from datastore.
 * Loads a requests and filters requests by status. Used to show all pending requests for review
 * queue.
 */

@WebServlet("/userHistory")
public class UserHistoryServlet extends HttpServlet {
  UserService userService = UserServiceFactory.getUserService();
  String userId = userService.getCurrentUser().getUserId();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    boolean isPendingRequest = Boolean.parseBoolean(request.getParameter("is-pending-request"));
    if (isPendingRequest) {
      Query<Review> query = ObjectifyService.ofy().load().type(Review.class);
      List<Review> allRequests = query.list();
      List<Review> pendingReviews = allRequests.stream()
                                        .filter(rev -> rev.userId.equals(userId))
                                        .filter(rev -> rev.status == Status.PENDING)
                                        .collect(toList());
      response.setContentType("application/json;");
      response.getWriter().println(new Gson().toJson(pendingReviews));
      return;
    } else {
      Query<Review> query = ObjectifyService.ofy().load().type(Review.class);
      List<Review> allRequests = query.list();
      List<Review> completedReviews = allRequests.stream()
                                          .filter(rev -> rev.userId.equals(userId))
                                          .filter(rev -> rev.status == Status.COMPLETED)
                                          .collect(toList());
      response.setContentType("application/json;");
      response.getWriter().println(new Gson().toJson(completedReviews));
      return;
    }
  }
}