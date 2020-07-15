package com.google.sps.servlets;

import com.google.gson.Gson;
import com.googlecode.objectify.ObjectifyService;
import java.io.IOException;
import java.lang.String;
import java.util.HashMap;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/request")
public class RequestServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      long requestId = Long.parseLong(request.getParameter("requestId"));
      Request userRequest = ObjectifyService.ofy().load().type(Request.class).id(requestId).now();
      response.setContentType("application/json;");
      response.getWriter().println(new Gson().toJson(userRequest));
    } catch (NumberFormatException e) {
      HashMap<String, Boolean> map = new HashMap<String, Boolean>();
      map.put("redirect", true);
      response.setContentType("application/json");
      response.getWriter().println(new Gson().toJson(map));
    }
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) {}
}