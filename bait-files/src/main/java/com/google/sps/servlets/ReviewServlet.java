package com.google.sps.servlets;

import com.google.gson.Gson;
import com.google.sps.servlets.Enums.Status;
import com.google.sps.servlets.Enums.Validity;
import com.googlecode.objectify.ObjectifyService;
import java.io.IOException;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet responsible for creating, saving, and loading review objects from datastore.
 * Servlet creates a review object and saves it to datastore then updates the request status, for
 * that review, indicating the results are avaiable to the user.
 */

@WebServlet("/review")
public class ReviewServlet extends HttpServlet {
  private static final String REVIEW_REQUEST_ID = "review-request-id";
  private static final String REVIEW_USER_ID = "review-user-id";
  private static final String NAME_VALIDITY = "name-validity";
  private static final String USERNAME_VALIDITY = "username-validity";
  private static final String EMAIL_VALIDITY = "email-validity";
  private static final String PHONE_VALIDITY = "phone-validity";
  private static final String ADDRESS_VALIDITY = "address-validity";
  private static final String IMAGE_VALIDITY = "image-validity";
  private static final String AUTHENTICITY_RATING = "authenticity-rating";
  private static final String REVIEWER_NOTES = "reviewer-notes";

  private class Result {
    Request request;
    Review review;

    private Result(Review review, Request request) {
      this.review = review;
      this.request = request;
    }
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      long reviewId = Long.parseLong(request.getParameter("reviewId"));
      Review userReview = ObjectifyService.ofy().load().type(Review.class).id(reviewId).now();

      if (userReview == null) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("redirect", "true");
        response.setContentType("application/json;");
        response.getWriter().println(new Gson().toJson(map));
        return;
      }

      Request userRequest = ObjectifyService.ofy()
                                .load()
                                .type(Request.class)
                                .id(Long.parseLong(userReview.requestId))
                                .now();
      if (userRequest == null) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("redirect", "true");
        response.setContentType("application/json;");
        response.getWriter().println(new Gson().toJson(map));
        return;
      }

      response.setContentType("application/json;");
      response.getWriter().println(new Gson().toJson(new Result(userReview, userRequest)));
    } catch (NumberFormatException e) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      Map<String, String[]> parameters = request.getParameterMap();
      Long reviewId = null;
      String requestId = parameters.get(
          REVIEW_REQUEST_ID)[0]; // There is only one value for each of keys in the parameters map
      String userId = parameters.get(REVIEW_USER_ID)[0];
      Validity nameValidity = Validity.valueOf(parameters.get(NAME_VALIDITY)[0].toUpperCase());
      Validity usernameValidity =
          Validity.valueOf(parameters.get(USERNAME_VALIDITY)[0].toUpperCase());
      Validity emailValidity = Validity.valueOf(parameters.get(EMAIL_VALIDITY)[0].toUpperCase());
      Validity phoneNumValidity = Validity.valueOf(parameters.get(PHONE_VALIDITY)[0].toUpperCase());
      Validity addressValidity =
          Validity.valueOf(parameters.get(ADDRESS_VALIDITY)[0].toUpperCase());
      Validity imageValidity = Validity.valueOf(parameters.get(IMAGE_VALIDITY)[0].toUpperCase());
      int authenticityRating = Integer.parseInt(parameters.get(AUTHENTICITY_RATING)[0]);
      String reviewerNotes = parameters.get(REVIEWER_NOTES)[0];

      ObjectifyService.ofy()
          .save()
          .entity(new Review(reviewId, userId, requestId, nameValidity, usernameValidity,
              emailValidity, phoneNumValidity, addressValidity, imageValidity, authenticityRating,
              reviewerNotes))
          .now();

      long pendingRequestId = Long.parseLong(requestId);
      Request pendingRequest =
          ObjectifyService.ofy().load().type(Request.class).id(pendingRequestId).now();
      pendingRequest.status = Status.COMPLETED;

      ObjectifyService.ofy().save().entity(pendingRequest).now();

      response.sendRedirect("/requests.html");
    } catch (IllegalArgumentException n) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
  }
}