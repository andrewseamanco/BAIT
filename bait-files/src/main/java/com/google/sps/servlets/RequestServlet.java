package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import com.google.sps.servlets.Address;
import com.google.sps.servlets.Request;
import com.googlecode.objectify.ObjectifyService;
import java.io.IOException;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;
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
  private static final String COUNTRY_CODE = "country-code-input";

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      long requestId = Long.parseLong(request.getParameter("requestId"));
      Request userRequest = ObjectifyService.ofy().load().type(Request.class).id(requestId).now();
      if (userRequest == null) {
        HashMap<String, String> responseParameters = new HashMap<String, String>();
        responseParameters.put("redirect", "true");
        response.setContentType("application/json;");
        response.getWriter().println(new Gson().toJson(responseParameters));
        return;
      }
      response.setContentType("application/json;");
      response.getWriter().println(new Gson().toJson(userRequest));
    } catch (NumberFormatException e) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Map<String, String[]> parameters = request.getParameterMap();
    Long requestId = null;

    // get user id
    UserService userService = UserServiceFactory.getUserService();
    String userId = userService.getCurrentUser().getUserId();

    String nameInput = parameters.get(NAME)[0];
    String usernameInput = parameters.get(USERNAME)[0];
    String emailInput = parameters.get(EMAIL)[0];
    String pictureInput = parameters.get(PICTURE)[0];
    String phoneInput = parameters.get(PHONE)[0];
    String notesInput = parameters.get(NOTES)[0];

    String countryCode = parameters.get(COUNTRY_CODE)[0];
    String city = parameters.get("city-input")[0];
    String addressLine1 = parameters.get("address-line-1-input")[0];
    String addressLine2 = parameters.get("address-line-2-input")[0];

    String province = "";
    String postalCode = "";
    String zipCode = "";
    String state = "";

    Address address = new Address();

    if (countryCode == "CA") {
      postalCode = parameters.get("postal-code-input")[0];
      province = parameters.get("province-input")[0];
    } else if (countryCode == "US") {
      state = parameters.get("state-input")[0];
      zipCode = parameters.get("zip-code-input")[0];
    }

    if (countryCode != "IDK") {
      address = new Address(
          addressLine1, addressLine2, city, postalCode, zipCode, state, province, countryCode);
    }

    ObjectifyService.ofy()
        .save()
        .entity(new Request(requestId, userId, nameInput, usernameInput, emailInput, address,
            pictureInput, phoneInput, notesInput))
        .now();

    response.sendRedirect("/success.jsp");
  }
}
