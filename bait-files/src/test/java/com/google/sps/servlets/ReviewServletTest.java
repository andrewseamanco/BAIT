package com.google.sps.servlets;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import com.google.cloud.datastore.testing.LocalDatastoreHelper;
import com.google.sps.servlets.Enums.Status;
import com.google.sps.servlets.Enums.Validity;
import com.google.sps.servlets.Request;
import com.google.sps.servlets.RequestServlet;
import com.google.sps.servlets.Review;
import com.google.sps.servlets.ReviewServlet;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import com.googlecode.objectify.util.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class ReviewServletTest {
  static LocalDatastoreHelper datastoreHelper = LocalDatastoreHelper.create(1.0);

  private Closeable objectify;
  StringWriter stringWriter = new StringWriter();
  PrintWriter writer = new PrintWriter(stringWriter);

  @BeforeClass
  public static void oneTimeSetUp() throws InterruptedException, IOException, TimeoutException {
    datastoreHelper.start();
  }

  @Before
  public void setUp() throws InterruptedException, IOException {
    datastoreHelper.reset();
    ObjectifyFactory factory = new ObjectifyFactory(datastoreHelper.getOptions().getService());
    ObjectifyService.init(factory);
    ObjectifyService.register(Request.class);
    ObjectifyService.register(Review.class);
    objectify = ObjectifyService.begin();
  }

  @After
  public void tearDown() throws InterruptedException, IOException, TimeoutException {
    objectify.close();
  }

  @AfterClass
  public static void oneTimeTearDown() throws InterruptedException, IOException, TimeoutException {
    datastoreHelper.stop();
  }

  @Test
  public void doGet_whenReviewExistsAndRequestDoesNotExist_returnsRedirectFlag()
      throws IOException, ServletException {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    ObjectifyService.ofy()
        .save()
        .entity(new Review(22L, "4", "14", Validity.VALID, Validity.VALID, Validity.INVALID,
            Validity.INVALID, Validity.VALID, Validity.INVALID, 3, "some more notes"))
        .now();

    when(request.getParameter("requestId")).thenReturn("14");
    when(request.getParameter("reviewId")).thenReturn("22");
    when(response.getWriter()).thenReturn(writer);
    new ReviewServlet().doGet(request, response);
    String rawJsonResponse = stringWriter.toString();
    assertTrue(rawJsonResponse.startsWith("{\"redirect\":\"true\""));
  }

  @Test
  public void doGet_whenRequestExistsAndReviewDoesNotExist_returnsRedirectFlag()
      throws IOException, ServletException {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    ObjectifyService.ofy()
        .save()
        .entity(new Request(14L, "4", "human", "human47", "human47@gmail.com", "2930 pearl street",
            "no_image", "555-555-5555", "some notes"))
        .now();

    when(request.getParameter("requestId")).thenReturn("14");
    when(request.getParameter("reviewId")).thenReturn("22");
    when(response.getWriter()).thenReturn(writer);
    new ReviewServlet().doGet(request, response);
    String rawJsonResponse = stringWriter.toString();
    assertTrue(rawJsonResponse.startsWith("{\"redirect\":\"true\""));
  }

  @Test
  public void doGet_whenReviewDoesNotExistAndRequestDoesNotExist_returnsRedirectFlag()
      throws IOException, ServletException {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    when(request.getParameter("requestId")).thenReturn("14");
    when(request.getParameter("reviewId")).thenReturn("22");
    when(response.getWriter()).thenReturn(writer);
    new ReviewServlet().doGet(request, response);
    String rawJsonResponse = stringWriter.toString();
    assertTrue(rawJsonResponse.startsWith("{\"redirect\":\"true\""));
  }

  @Test
  public void doGet_whenReviewExistsAndRequestExists_returnsReviewAndRequest()
      throws IOException, ServletException {
    ObjectifyService.ofy()
        .save()
        .entity(new Request(14L, "4", "human", "human47", "human47@gmail.com", "2930 pearl street",
            "no_image", "555-555-5555", "some notes"))
        .now();

    ObjectifyService.ofy()
        .save()
        .entity(new Review(22L, "4", "14", Validity.VALID, Validity.VALID, Validity.INVALID,
            Validity.INVALID, Validity.VALID, Validity.INVALID, 3, "some more notes"))
        .now();

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    when(request.getParameter("reviewId")).thenReturn("22");
    when(request.getParameter("requestId")).thenReturn("14");
    when(response.getWriter()).thenReturn(writer);
    new ReviewServlet().doGet(request, response);
    String rawJsonResponse = stringWriter.toString();
    assertTrue(rawJsonResponse.startsWith("{\"request\":{\"requestId\":14,"));
  }

  @Test
  public void doPost_whenOneReviewCreated_StoresOneReview() throws IOException, ServletException {
    ObjectifyService.ofy()
        .save()
        .entity(new Request(14L, "4", "human", "human47", "human47@gmail.com", "2930 pearl street",
            "no_image", "555-555-5555", "some notes"))
        .now();

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    Map<String, String[]> parameters = new HashMap<String, String[]>();
    parameters.put("review-request-id", new String[] {"14"});
    parameters.put("review-user-id", new String[] {"4"});
    parameters.put("name-validity", new String[] {"valid"});
    parameters.put("username-validity", new String[] {"valid"});
    parameters.put("email-validity", new String[] {"invalid"});
    parameters.put("phone-validity", new String[] {"invalid"});
    parameters.put("address-validity", new String[] {"valid"});
    parameters.put("image-validity", new String[] {"invalid"});
    parameters.put("authenticity-rating", new String[] {"2"});
    parameters.put("reviewer-notes", new String[] {"Look Like You Know - Royal Blood"});

    when(request.getParameterMap()).thenReturn(parameters);
    when(request.getParameter("status")).thenReturn("COMPLETED");
    when(response.getWriter()).thenReturn(writer);
    new ReviewServlet().doPost(request, response);
    Query<Review> query = ObjectifyService.ofy().load().type(Review.class);
    List<Review> allReviews = query.list();
    assertTrue(allReviews.size() == 1);
  }

  @Test
  public void doPost_parameterMissing_sendsError() throws IOException, ServletException {
    ObjectifyService.ofy()
        .save()
        .entity(new Request(14L, "4", "human", "human47", "human47@gmail.com", "2930 pearl street",
            "no_image", "555-555-5555", "some notes"))
        .now();

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    Map<String, String[]> parameters = new HashMap<String, String[]>();
    parameters.put("review-request-id", new String[] {"14"});
    parameters.put("review-user-id", new String[] {"4"});
    parameters.put("name-validity", new String[] {"valid"});
    parameters.put("username-validity", new String[] {"valid"});
    parameters.put("email-validity", new String[] {"invalid"});
    parameters.put("phone-validity", new String[] {"invalid"});
    parameters.put("address-validity", new String[] {""}); // missing parameter value
    parameters.put("image-validity", new String[] {"invalid"});
    parameters.put("authenticity-rating", new String[] {"2"});
    parameters.put("reviewer-notes", new String[] {"Look Like You Know - Royal Blood"});
    when(request.getParameterMap()).thenReturn(parameters);
    when(request.getParameter("status")).thenReturn("COMPLETED");
    when(response.getWriter()).thenReturn(writer);
    new ReviewServlet().doPost(request, response);
    Query<Review> query = ObjectifyService.ofy().load().type(Review.class);
    List<Review> allReviews = query.list();
    verify(response).sendError(400);
    assertTrue(allReviews.size() == 0);
  }
}