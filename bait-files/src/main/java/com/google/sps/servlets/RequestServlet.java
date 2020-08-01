package com.google.sps.servlets;

import static java.util.stream.Collectors.toList;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
<<<<<<< HEAD
import com.google.sps.servlets.Address;
=======
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
>>>>>>> cd0bac2d4fbc440a43c72085e35d269d39b92ca7
import com.google.sps.servlets.Request;
import com.google.sps.servlets.Url;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import java.io.IOException;
import java.lang.String;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
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
  private static final String COUNTRY_CODE = "country";
  private static final String CITY = "city-input";
  private static final String ADDRESS_1 = "address-line-1-input";
  private static final String ADDRESS_2 = "address-line-2-input";
  private static final String POSTAL_ZIP = "postal-zip-code-input";
  private static final String STATE_PROVINCE = "state-province-input";
  private static final String API_ERROR = "The request to this API failed.";
  private static HttpClient client;

  public RequestServlet() {
    this.client = HttpClient.newHttpClient();
  }

  public RequestServlet(HttpClient client) {
    this.client = client;
  }

  // Wrapper class for Request object and API result JsonObjects
  private class RequestWrapper {
    Request request;
    JsonObject phoneResults;
    JsonObject emailResults;

    private RequestWrapper(Request request, JsonObject phoneResults, JsonObject emailResults) {
      this.request = request;
      this.phoneResults = phoneResults;
      this.emailResults = emailResults;
    }
  }

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

      JsonObject phoneResults = getPhoneApiResults(userRequest.phoneNum);
      JsonObject emailResults = getEmailApiResults(userRequest.email);

      response.setContentType("application/json;");
      response.getWriter().println(
          new Gson().toJson(new RequestWrapper(userRequest, phoneResults, emailResults)));
    } catch (NumberFormatException e) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Map<String, String[]> parameters = request.getParameterMap();
    Long requestId = null;
    
    UserService userService = UserServiceFactory.getUserService();
    String userId = userService.getCurrentUser().getUserId();

    String nameInput = parameters.get(NAME)[0];
    String usernameInput = parameters.get(USERNAME)[0];
    String emailInput = parameters.get(EMAIL)[0];
    String pictureInput = parameters.get(PICTURE)[0];
    String phoneInput = parameters.get(PHONE)[0];
    String notesInput = parameters.get(NOTES)[0];

    String countryCode = parameters.get(COUNTRY_CODE)[0];
    String city = parameters.get(CITY)[0];
    String addressLine1 = parameters.get(ADDRESS_1)[0];
    String addressLine2 = parameters.get(ADDRESS_2)[0];

    Address address = new Address();

    if (countryCode.equals("CA")) {
      String postalCode = parameters.get(POSTAL_ZIP)[0];
      String province = parameters.get(STATE_PROVINCE)[0];
      address =
          new Address(addressLine1, addressLine2, city, postalCode, "", "", province, countryCode);
    } else if (countryCode.equals("US")) {
      String state = parameters.get(STATE_PROVINCE)[0];
      String zipCode = parameters.get(POSTAL_ZIP)[0];
      address = new Address(addressLine1, addressLine2, city, "", zipCode, state, "", countryCode);
    }

    ObjectifyService.ofy()
        .save()
        .entity(new Request(requestId, userId, nameInput, usernameInput, emailInput, address,
            pictureInput, phoneInput, notesInput))
        .now();

    response.sendRedirect("/success.jsp");
  }

  private String doGetAPI(String url) throws IOException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    return response.body();
  }

  private JsonObject getPhoneApiResults(String phoneNum) throws IOException {
    Query<Url> query = ObjectifyService.ofy().load().type(Url.class);
    List<Url> allUrls = query.list();
    List<Url> phoneUrl = allUrls.stream().filter(url -> url.name == "phone-api").collect(toList());
    JsonObject phoneResults = new JsonObject();
    if (phoneUrl.size() == 0 || phoneNum.isEmpty()) {
      return phoneResults;
    }

    Url url = phoneUrl.get(0);
    try {
      phoneResults = JsonParser.parseString(doGetAPI(url.url + phoneNum)).getAsJsonObject();
    } catch (InterruptedException e) {
      phoneResults = JsonParser.parseString("{\"results_unavailable\": true}").getAsJsonObject();
    }

    return phoneResults;
  }

  private JsonObject getEmailApiResults(String email) throws IOException {
    Query<Url> query = ObjectifyService.ofy().load().type(Url.class);
    List<Url> allUrls = query.list();
    List<Url> emailUrl = allUrls.stream().filter(url -> url.name == "email-api").collect(toList());
    JsonObject emailResults = new JsonObject();
    if (emailUrl.size() == 0 || email.isEmpty()) {
      return emailResults;
    }

    Url url = emailUrl.get(0);
    try {
      emailResults = JsonParser.parseString(doGetAPI(url.url + email)).getAsJsonObject();
    } catch (InterruptedException e) {
      emailResults = JsonParser.parseString("{\"results_unavailable\": true}").getAsJsonObject();
    }

    return emailResults;
  }
}
