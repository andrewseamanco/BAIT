package com.google.sps.servlets;

import com.google.gson.Gson;
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
  private static final String COMPLETED = "Completed";

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {}

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    //create review object and save
    Map<String, String[]> parameters = request.getParameterMap();
    Long reviewId = null;
    String requestId = parameters.get("review-request-id")[0];
    String userId = parameters.get("review-user-id")[0];   
    String status = COMPLETED;
    String nameValidity = parameters.get("name-validity")[0];
    String usernameValidity = parameters.get("username-validity")[0];
    String emailValidity = parameters.get("email-validity")[0];
    String phoneNumValidity = parameters.get("phone-validity")[0];
    String addressValidity = parameters.get("address-validity")[0];
    String imageValidity = parameters.get("image-validity")[0];
    String authenticityRating = parameters.get("authenticity-rating")[0];
    String reviewerNotes = parameters.get("reviewer-notes")[0];
    long submissionDate = System.currentTimeMillis();

    ObjectifyService.ofy().save().entity(new Review(reviewId, userId, requestId, status, nameValidity,usernameValidity, phoneNumValidity, addressValidity, imageValidity,authenticityRating,reviewerNotes,submissionDate)).now();

    //update request object status
    long reqId = Long.parseLong(requestId);
    Request req = ObjectifyService.ofy().load().type(Request.class).id(reqId).now();
    req.status = COMPLETED;
    
    ObjectifyService.ofy().save().entity(req).now();
    
    response.sendRedirect("/reviews.html");
  }
}