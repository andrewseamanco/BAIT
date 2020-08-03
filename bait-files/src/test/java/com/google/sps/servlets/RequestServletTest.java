package com.google.sps.servlets;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.cloud.datastore.testing.LocalDatastoreHelper;
import com.google.sps.servlets.Address;
import com.google.sps.servlets.Request;
import com.google.sps.servlets.RequestServlet;
import com.google.sps.servlets.Url;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import com.googlecode.objectify.util.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.concurrent.TimeoutException;
import javax.servlet.ServletException;
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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class RequestServletTest {
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
    ObjectifyService.register(Url.class);
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
  public void doGet_whenOneRequestInDb_returnsOneRequest() throws IOException, ServletException {
    ObjectifyService.ofy()
        .save()
        .entity(new Request(14L, "4", "human", "human47", "human47@gmail.com", new Address(),
            "no_image", "555-555-5555", "some notes"))
        .now();

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    CloseableHttpClient client = mock(CloseableHttpClient.class);

    when(request.getParameter("requestId")).thenReturn("14");
    when(response.getWriter()).thenReturn(writer);

    new RequestServlet(client).doGet(request, response);

    String rawJsonResponse = stringWriter.toString();
    assertTrue(rawJsonResponse.startsWith("{\"request\":{\"requestId\":14,"));
  }

  @Test
  public void doGet_whenRequestDoesNotExist_returnsNothing() throws IOException, ServletException {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    when(request.getParameter("requestId")).thenReturn("14");

    when(response.getWriter()).thenReturn(writer);

    new RequestServlet().doGet(request, response);

    String rawJsonResponse = stringWriter.toString();
    assertTrue(rawJsonResponse.startsWith("{\"redirect\":\"true\""));
  }

  @Test
  public void doGet_whenPhoneApiCalled_returnsResults()
      throws IOException, ServletException, InterruptedException {
    ObjectifyService.ofy()
        .save()
        .entity(new Request(14L, "4", "human", "human47", "human47@gmail.com", new Address(),
            "no_image", "555-555-5555", "some notes"))
        .now();

    Url url = new Url(19L, "phone-api", "http://www.phoneapi.com/");

    ObjectifyService.ofy().save().entity(url).now();

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    CloseableHttpClient client = mock(CloseableHttpClient.class);
    ResponseHandler<String> ResponseHandler = mock(ResponseHandler.class);
    HttpGet httpget = new HttpGet(url.url);

    when(request.getParameter("requestId")).thenReturn("14");
    when(response.getWriter()).thenReturn(writer);
    when(ResponseHandler.handleResponse(any())).thenReturn("null");
    when(client.execute(httpget, ResponseHandler)).thenReturn("{\"testing\":\"true\"}");

    new RequestServlet(client).doGet(request, response);

    String rawJsonResponse = stringWriter.toString();
    assertTrue(rawJsonResponse.startsWith("{\"request\":{\"requestId\":14,"));
  }

  @Test
  public void doGet_whenEmailApiCalled_returnsResults()
      throws IOException, ServletException, InterruptedException {
    ObjectifyService.ofy()
        .save()
        .entity(new Request(14L, "4", "human", "human47", "human47@gmail.com", new Address(),
            "no_image", "555-555-5555", "some notes"))
        .now();

    Url url = new Url(19L, "email-api", "http://www.emailapi.com/");

    ObjectifyService.ofy().save().entity(url).now();

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    CloseableHttpClient client = mock(CloseableHttpClient.class);
    ResponseHandler<String> ResponseHandler = mock(ResponseHandler.class);
    HttpGet httpget = new HttpGet(url.url);

    when(request.getParameter("requestId")).thenReturn("14");
    when(response.getWriter()).thenReturn(writer);
    when(ResponseHandler.handleResponse(any())).thenReturn("null");
    when(client.execute(httpget, ResponseHandler)).thenReturn("{\"testing\":\"true\"}");

    new RequestServlet(client).doGet(request, response);

    String rawJsonResponse = stringWriter.toString();
    assertTrue(rawJsonResponse.startsWith("{\"request\":{\"requestId\":14,"));
  }
}
