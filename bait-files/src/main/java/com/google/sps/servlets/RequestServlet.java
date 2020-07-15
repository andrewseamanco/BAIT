package com.google.sps.servlets;

import com.google.gson.Gson;
import com.googlecode.objectify.ObjectifyService;
import java.io.IOException;
import java.lang.String;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/request")
public class RequestServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // TODO: Add error checking and redirection for non-Numeric/non-long requestId values
    long requestId = Long.parseLong(request.getParameter("requestId"));
    Request userRequest = ObjectifyService.ofy().load().type(Request.class).id(requestId).now();
    response.setContentType("application/json;");
    response.getWriter().println(new Gson().toJson(userRequest));
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) {}
}