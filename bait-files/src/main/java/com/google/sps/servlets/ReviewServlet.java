package com.google.sps.servlets;

import com.google.gson.Gson;
import com.google.sps.servlets.Enums.Status;
import com.google.sps.servlets.Enums.Validity;
import com.googlecode.objectify.ObjectifyService;
import java.io.IOException;
import java.lang.String;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/** Servlet responsible for creating, saving, and loading review objects from datastore.
* Servlet creates a review object and saves it to datastore then updates the request status, for that review, indicating the results are avaiable to the user.
*/

@WebServlet("/review")
public class ReviewServlet extends HttpServlet {
  private static final String REVIEWREQUESTID = "review-request-id";
  private static final String REVIEWUSERID = "review-user-id";
  private static final String NAMEVALIDITY = "name-validity";
  private static final String USERNAMEVALIDITY = "username-validity";
  private static final String EMAILVALIDITY = "email-validity";
  private static final String PHONEVALIDITY = "phone-validity";
  private static final String ADDRESSVALIDITY = "address-validity";
  private static final String IMAGEVALIDITY = "image-validity";
  private static final String AUTHENTICITYRATING = "authenticity-rating";
  private static final String REVIEWERNOTES = "reviewer-notes";

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {}

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Map<String, String[]> parameters = request.getParameterMap();
    Long reviewId = null;
    String requestId = parameters.get(REVIEWREQUESTID)[0]; // There is only one value for each of keys in the parameters map
    String userId = parameters.get(REVIEWUSERID)[0];
    Validity nameValidity = Validity.valueOf(parameters.get(NAMEVALIDITY)[0].toUpperCase());
    Validity usernameValidity = Validity.valueOf(parameters.get(USERNAMEVALIDITY)[0].toUpperCase());
    Validity emailValidity = Validity.valueOf(parameters.get(EMAILVALIDITY)[0].toUpperCase());
    Validity phoneNumValidity = Validity.valueOf(parameters.get(PHONEVALIDITY)[0].toUpperCase());
    Validity addressValidity = Validity.valueOf(parameters.get(ADDRESSVALIDITY)[0].toUpperCase());
    Validity imageValidity = Validity.valueOf(parameters.get(IMAGEVALIDITY)[0].toUpperCase());
    int authenticityRating = Integer.parseInt(parameters.get(AUTHENTICITYRATING)[0]);
    String reviewerNotes = parameters.get(REVIEWERNOTES)[0];

    ObjectifyService.ofy()
        .save()
        .entity(new Review(reviewId, userId, requestId, nameValidity, usernameValidity,
            emailValidity, phoneNumValidity, addressValidity, imageValidity, authenticityRating,
            reviewerNotes))
        .now();

    long pendingRequestId = Long.parseLong(requestId);
    Request pendingRequest = ObjectifyService.ofy().load().type(Request.class).id(pendingRequestId).now();
    pendingRequest.status = Status.COMPLETED;

    ObjectifyService.ofy().save().entity(pendingRequest).now();

    response.sendRedirect("/reviews.html");
  }
}