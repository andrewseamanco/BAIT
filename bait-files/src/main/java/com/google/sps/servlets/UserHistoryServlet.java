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
import java.util.Collections;
import java.util.Comparator;
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
<<<<<<< HEAD

=======
>>>>>>> 73efd9c7c60b8ba5bb0bcd06078d7412b7dd3578
  UserService userService = UserServiceFactory.getUserService();
  String userId = userService.getCurrentUser().getUserId();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    boolean isPendingReview = Boolean.parseBoolean(request.getParameter("is-pending-review"));
    if (isPendingReview) {
      List<Request> allRequests = ObjectifyService.ofy().load().type(Request.class).list();
      List<Request> requests = allRequests.stream()
                                   .filter(req -> req.userId.equals(userId))
                                   .filter(req -> req.status == Status.PENDING)
                                   .sorted(Comparator.comparing(req -> req.submissionDate))
                                   .collect(toList());
      ;
      response.setContentType("application/json;");
      response.getWriter().println(new Gson().toJson(requests));
      return;
    } else {
      List<Review> allReviews = ObjectifyService.ofy().load().type(Review.class).list();
      List<Review> reviews = allReviews.stream()
                                 .filter(review -> review.userId.equals(userId))
                                 .filter(review -> review.status == Status.COMPLETED)
                                 .sorted(Comparator.comparing(review -> review.submissionDate))
                                 .collect(toList());
      Collections.reverse(reviews);
      response.setContentType("application/json;");
      response.getWriter().println(new Gson().toJson(reviews));
      return;
    }
  }

  public List<Review> getReviewsByType(Status statusType) {
    List<Review> allRequests = ObjectifyService.ofy().load().type(Review.class).list();
    List<Review> reviews = allRequests.stream()
                               .filter(rev -> rev.userId.equals(userId))
                               .filter(rev -> rev.status == statusType)
                               .sorted(Comparator.comparing(rev -> rev.submissionDate))
                               .collect(toList());

    return reviews;
  }
}