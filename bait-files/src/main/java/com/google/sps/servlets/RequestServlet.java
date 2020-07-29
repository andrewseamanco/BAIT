package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.sps.servlets.Request;
import com.google.sps.servlets.Url;
import com.googlecode.objectify.ObjectifyService;
import java.io.IOException;
import java.lang.String;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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
  private static final String API_ERROR = "The request to this API failed.";

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
      JsonObject phoneResults = new JsonObject();

      Url phoneUrl =
          ObjectifyService.ofy().load().type(Url.class).filter("name", "phone-api").first().now();
      if (phoneUrl != null && userRequest.phoneNum != null
          && userRequest.phoneNum != "") { // check if userRequest is null - don't call api if it is
        try {
          phoneResults =
              JsonParser.parseString(doGetAPI(phoneUrl.url + "613-413-9716")).getAsJsonObject();
        } catch (InterruptedException e) {
        }
      }

      Url emailUrl =
          ObjectifyService.ofy().load().type(Url.class).filter("name", "email-api").first().now();
      JsonObject emailResults = new JsonObject();
      if (emailUrl != null && userRequest.email != null && userRequest.email != "") {
        try {
          emailResults = JsonParser.parseString(doGetAPI(emailUrl.url + "imanherzi@gmail.com"))
                             .getAsJsonObject();
        } catch (InterruptedException e) {
        }
      }

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

    // get user id
    UserService userService = UserServiceFactory.getUserService();
    String userId = userService.getCurrentUser().getUserId();

    String nameInput = parameters.get(NAME)[0];
    String usernameInput = parameters.get(USERNAME)[0];
    String emailInput = parameters.get(EMAIL)[0];
    String addressInput = parameters.get(ADDRESS)[0];
    String pictureInput = parameters.get(PICTURE)[0];
    String phoneInput = parameters.get(PHONE)[0];
    String notesInput = parameters.get(NOTES)[0];

    ObjectifyService.ofy()
        .save()
        .entity(new Request(requestId, userId, nameInput, usernameInput, emailInput, addressInput,
            pictureInput, phoneInput, notesInput))
        .now();

    response.sendRedirect("/history.jsp");
  }

  public String doGetAPI(String url) throws IOException, InterruptedException {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    return response.body();
  }
}
