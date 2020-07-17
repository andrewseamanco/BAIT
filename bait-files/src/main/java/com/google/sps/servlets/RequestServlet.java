package com.google.sps.servlets;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.gson.Gson;
import com.google.sps.servlets.Request;
import com.googlecode.objectify.ObjectifyService;
import java.io.IOException;
import java.lang.String;
import java.util.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet responsible for creating requests. */
@WebServlet("/request")
public class RequestServlet extends HttpServlet {
  private static final String NAME = "name-input";
  private static final String USERNAME = "username-input";
  private static final String EMAIL = "email-input";
  private static final String ADDRESS = "address-input";
  private static final String PICTURE = "picture-input";
  private static final String PHONE = "phone-input";
  private static final String NOTES = "notes-input";

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {}

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Map<String, String[]> parameters = request.getParameterMap();
    Long requestId = null;
    // will get user id from input when connected
    // String userIdInput = parameters.get("user-id-input")[0];
    String nameInput = parameters.get(NAME)[0];
    String usernameInput = parameters.get(USERNAME)[0];
    String emailInput = parameters.get(EMAIL)[0];
    String addressInput = parameters.get(ADDRESS)[0];
    String pictureInput = parameters.get(PICTURE)[0];
    String phoneInput = parameters.get(PHONE)[0];
    String notesInput = parameters.get(NOTES)[0];

    // id is 1 for now by default until I can get userId from user feature
    ObjectifyService.ofy()
        .save()
        .entity(new Request(requestId, 1, nameInput, usernameInput, emailInput, addressInput,
            pictureInput, phoneInput, notesInput))
        .now();

    response.sendRedirect("/submission.html");
  }
}
