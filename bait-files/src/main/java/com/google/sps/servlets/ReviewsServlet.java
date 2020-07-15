package com.google.sps.servlets;

import com.google.gson.Gson;
import com.googlecode.objectify.ObjectifyService;
import java.io.IOException;
import java.lang.String;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/reviews")
public class ReviewsServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // TODO: Add error checking and redirection for non-Numeric/non-long requestId values
    List<Request> pendingRequests = ObjectifyService.ofy().load().type(Request.class).filter("status ==", "Pending").list();
    response.setContentType("application/json;");
    response.getWriter().println(new Gson().toJson(pendingRequests));
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) {}
}