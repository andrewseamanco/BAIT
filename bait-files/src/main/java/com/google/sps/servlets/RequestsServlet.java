package com.google.sps.servlets;

import static java.util.stream.Collectors.toList;

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
 * Loads a requests and filters requests by status. Used to show all pending requests for review queue.
 */

@WebServlet("/requests")
public class RequestsServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query<Request> query = ObjectifyService.ofy().load().type(Request.class);
    List<Request> allRequests = query.list();
    List<Request> pendingRequests =
        allRequests.stream().filter(req -> req.status == Status.PENDING).collect(toList());

    response.setContentType("application/json");
    response.getWriter().println(new Gson().toJson(pendingRequests));
  }

}