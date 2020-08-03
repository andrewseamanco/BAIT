package com.google.sps.servlets;

import static java.util.stream.Collectors.toList;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.sps.servlets.Address;
import com.google.sps.servlets.Request;
import com.google.sps.servlets.Url;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import java.io.IOException;
import java.lang.String;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

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
  private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
  private static final String COUNTRY_CODE = "country";
  private static final String CITY = "city-input";
  private static final String ADDRESS_1 = "address-line-1-input";
  private static final String ADDRESS_2 = "address-line-2-input";
  private static final String POSTAL_ZIP = "postal-zip-code-input";
  private static final String STATE_PROVINCE = "state-province-input";
  private static final String API_ERROR = "The request to this API failed.";
  private CloseableHttpClient client = HttpClients.createDefault();

  public RequestServlet() {
    this.client = HttpClients.createDefault();
  }

  public RequestServlet(CloseableHttpClient client) {
    this.client = client;
  }

  // Wrapper class for Request object and API result JsonObjects
  private class RequestWrapper {
    Request request;
    JsonObject phoneResults;
    JsonObject emailResults;
    JsonObject addressResults;

    private RequestWrapper(Request request, JsonObject phoneResults, JsonObject emailResults,
        JsonObject addressResults) {
      this.request = request;
      this.phoneResults = phoneResults;
      this.emailResults = emailResults;
      this.addressResults = addressResults;
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
      JsonObject addressResults = getAddressApiResults(userRequest.address);

      response.setContentType("application/json;");
      response.getWriter().println(new Gson().toJson(
          new RequestWrapper(userRequest, phoneResults, emailResults, addressResults)));
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
    String blobKeyString = getUploadedFileUrl(request);
    String phoneInput = parameters.get(PHONE)[0];
    String notesInput = parameters.get(NOTES)[0];
    String countryCode = parameters.get(COUNTRY_CODE)[0];
    String city = parameters.get(CITY)[0];
    String addressLine1 = parameters.get(ADDRESS_1)[0];
    addressLine1 = addressLine1.replaceAll("\\s+", "");
    addressLine1 = addressLine1.trim();
    String addressLine2 = parameters.get(ADDRESS_2)[0];
    addressLine2 = addressLine2.replaceAll("\\s+", "");
    addressLine2 = addressLine2.trim();

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
            blobKeyString, phoneInput, notesInput))
        .now();

    response.sendRedirect("/success.jsp");
  }

  /** Gets the URL of the file the user uploaded and checks for no file uploaded */
  private String getUploadedFileUrl(HttpServletRequest request) {
    Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
    List<BlobKey> blobKeys = blobs.get(PICTURE);

    // User submitted form without selecting a file, so we can't get a URL. (dev server)
    if (blobKeys == null || blobKeys.isEmpty()) {
      return null;
    }

    // Our form only contains a single file input, so get the first index.
    BlobKey blobKey = blobKeys.get(0);

    // User submitted form without selecting a file, so we can't get a URL. (live server)
    BlobInfo blobInfo = new BlobInfoFactory().loadBlobInfo(blobKey);
    if (blobInfo.getSize() == 0) {
      blobstoreService.delete(blobKey);
      return null;
    }

    return blobKey.getKeyString();
  }

  private String doGetAPI(String url) throws IOException, InterruptedException {
    client = HttpClients.createDefault();
    try {
      HttpGet httpget = new HttpGet(url);
      ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
        @Override
        public String handleResponse(final HttpResponse response)
            throws ClientProtocolException, IOException {
          int status = response.getStatusLine().getStatusCode();
          if (status >= 200 && status < 300) {
            HttpEntity entity = response.getEntity();
            return entity != null ? EntityUtils.toString(entity) : null;
          } else {
            throw new ClientProtocolException("Unexpected response status: " + status);
          }
        }
      };
      String responseBody = client.execute(httpget, responseHandler);
      client.close();
      return responseBody;
    } catch (Exception e) {
      return "{\"results_unavailable\": true}";
    }
  }

  private JsonObject getPhoneApiResults(String phoneNum) throws IOException {
    Query<Url> query = ObjectifyService.ofy().load().type(Url.class);
    List<Url> allUrls = query.list();
    List<Url> phoneUrl =
        allUrls.stream().filter(url -> url.name.equals("phone-api")).collect(toList());
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
    List<Url> emailUrl =
        allUrls.stream().filter(url -> url.name.equals("email-api")).collect(toList());
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

  private JsonObject getAddressApiResults(Address address) throws IOException {
    Query<Url> query = ObjectifyService.ofy().load().type(Url.class);
    List<Url> allUrls = query.list();
    List<Url> addressUrl =
        allUrls.stream().filter(url -> url.name.equals("address-api")).collect(toList());
    JsonObject addressResults = new JsonObject();
    if (addressUrl.size() == 0 || address.countryCode.isEmpty()) {
      return addressResults;
    }

    Url url = addressUrl.get(0);
    String urlString = url.url + "&street_line_1=" + address.addressLine1 + "&street_line_2="
        + address.addressLine2 + "&city=" + address.city + "&state_code=" + address.state
        + "&postal_code=" + address.zipCode + "&country_code=" + address.countryCode;

    if (address.countryCode.equals("CA")) {
      urlString = url.url + "&street_line_1=" + address.addressLine1 + "&street_line_2="
          + address.addressLine2 + "&city=" + address.city + "&state_code=" + address.province
          + "&postal_code=" + address.postalCode + "&country_code=" + address.countryCode;
    }

    try {
      addressResults = JsonParser.parseString(doGetAPI(urlString)).getAsJsonObject();
    } catch (InterruptedException e) {
      addressResults = JsonParser.parseString("{\"results_unavailable\": true}").getAsJsonObject();
    }

    return addressResults;
  }
}