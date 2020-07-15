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

@WebServlet("/review")
public class ReviewServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {}

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Map<String, String[]> parameters = request.getParameterMap();
    Long reviewId = null;
    String requestId = parameters.get(
        "review-request-id")[0]; // There is only one value for each of keys in the parameters map
    String userId = parameters.get("review-user-id")[0];
    Validity nameValidity = Validity.valueOf(parameters.get("name-validity")[0].toUpperCase());
    Validity usernameValidity = Validity.valueOf(parameters.get("username-validity")[0].toUpperCase());
    Validity emailValidity = Validity.valueOf(parameters.get("email-validity")[0].toUpperCase());
    Validity phoneNumValidity = Validity.valueOf(parameters.get("phone-validity")[0].toUpperCase());
    Validity addressValidity = Validity.valueOf(parameters.get("address-validity")[0].toUpperCase());
    Validity imageValidity = Validity.valueOf(parameters.get("image-validity")[0].toUpperCase());
    int authenticityRating = Integer.parseInt(parameters.get("authenticity-rating")[0]);
    String reviewerNotes = parameters.get("reviewer-notes")[0];

    ObjectifyService.ofy()
        .save()
        .entity(new Review(reviewId, userId, requestId, nameValidity, usernameValidity,
            emailValidity, phoneNumValidity, addressValidity, imageValidity, authenticityRating,
            reviewerNotes))
        .now();

    long reqId = Long.parseLong(requestId);
    Request req = ObjectifyService.ofy().load().type(Request.class).id(reqId).now();
    req.status = Status.COMPLETED;

    ObjectifyService.ofy().save().entity(req).now();

    response.sendRedirect("/reviews.html");
  }
}