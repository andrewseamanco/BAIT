package com.google.sps.servlets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.cloud.datastore.testing.LocalDatastoreHelper;
import com.google.sps.servlets.Address;
import com.google.sps.servlets.Enums.Status;
import com.google.sps.servlets.Request;
import com.google.sps.servlets.RequestsServlet;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
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
public final class RequestsServletTest {
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
  public void doGet_whenNoRequestInDb_returnsNothing() throws IOException, ServletException {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    when(response.getWriter()).thenReturn(writer);

    new RequestsServlet().doGet(request, response);

    String rawJsonResponse = stringWriter.toString();
    assertTrue(rawJsonResponse.startsWith("[]"));
  }

  @Test
  public void doGet_whenOneRequestInDb_returnsOneRequest() throws IOException, ServletException {
    ObjectifyService.ofy()
        .save()
        .entity(new Request(14L, "4", "human", "human47", "human47@gmail.com", new Address(),
            "no_image", "719-325-6872", "some notes"))
        .now();

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    when(response.getWriter()).thenReturn(writer);

    new RequestsServlet().doGet(request, response);

    String rawJsonResponse = stringWriter.toString();
    assertTrue(rawJsonResponse.startsWith("[{\"requestId\":14"));
  }

  @Test
  public void doGet_returnsOnlyPendingRequests() throws IOException, ServletException {
    ObjectifyService.ofy()
        .save()
        .entity(new Request(14L, "4", "human", "human47", "human47@gmail.com", new Address(),
            "no_image", "555-555-5555", "some notes"))
        .now();

    ObjectifyService.ofy()
        .save()
        .entity(new Request(19L, "4", "human", "human47", "human47@gmail.com", new Address(),
            "no_image", "555-555-5555", "some more notes"))
        .now();

    Request pendingRequest =
        ObjectifyService.ofy().load().type(Request.class).id(Long.parseLong("19")).now();
    pendingRequest.status = Status.COMPLETED;

    ObjectifyService.ofy().save().entity(pendingRequest).now();

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    when(response.getWriter()).thenReturn(writer);

    new RequestsServlet().doGet(request, response);

    String rawJsonResponse = stringWriter.toString();
    String submissionDate = rawJsonResponse.substring(66, 79);
    String expected =
        "[{\"requestId\":14,\"userId\":\"4\",\"status\":\"PENDING\",\"submissionDate\":"
        + submissionDate
        + ",\"name\":\"human\",\"username\":\"human47\",\"email\":\"human47@gmail.com\",\"address\":{\"addressLine1\":\"\",\"addressLine2\":\"\",\"city\":\"\",\"postalCode\":\"\",\"zipCode\":\"\",\"countryCode\":\"\",\"state\":\"\",\"province\":\"\"},\"image\":\"no_image\",\"phoneNum\":\"555-555-5555\",\"notes\":\"some notes\"}]";

    assertTrue(rawJsonResponse.startsWith(expected));
  }
}
