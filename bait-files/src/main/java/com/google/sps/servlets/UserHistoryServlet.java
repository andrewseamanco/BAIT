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
import java. util.Comparator;
import java. util.Collections;


/**
 * Servlet loads all pending requests from datastore.
 * Loads a requests and filters requests by status. Used to show all pending requests for review
 * queue.
 */

@WebServlet("/userHistory")
public class UserHistoryServlet extends HttpServlet {
  class SortByMostRecentDate implements Comparator<Review> { 
    public int compare(Review a, Review b) {
        Long aDate = a.submissionDate;
        Long bDate = b.submissionDate;
           if(aDate == bDate) {
        return 0;
     }
     if(aDate < bDate) {
        return -1; //return negative integer if first argument is less than second
     }
     return 1;
    } 
  }

  UserService userService = UserServiceFactory.getUserService();
  String userId = userService.getCurrentUser().getUserId();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    boolean isPendingReview = Boolean.parseBoolean(request.getParameter("is-pending-review"));
    if (isPendingReview) {
      List<Review> pendingReviews = getReviewsByType(Status.PENDING);
      response.setContentType("application/json;");
      response.getWriter().println(new Gson().toJson(pendingReviews));
      return;
    } else {
      List<Review> completedReviews = getReviewsByType(Status.COMPLETED);
      response.setContentType("application/json;");
      response.getWriter().println(new Gson().toJson(completedReviews));
      return;
    }
  }

  public List<Review> getReviewsByType(Status statusType)  {
      List<Review> allRequests = ObjectifyService.ofy().load().type(Review.class).list();
      List<Review> reviews = allRequests.stream()
                                          .filter(rev -> rev.userId.equals(userId))
                                          .filter(rev -> rev.status == statusType)
                                          .collect(toList());
      Collections.sort(reviews, new SortByMostRecentDate());

      return reviews;
  }
}