package com.google.sps;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.cloud.datastore.testing.LocalDatastoreHelper;
import com.google.sps.servlets.Request;
import com.google.sps.servlets.RequestServlet;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
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
        .entity(new Request(14L, "4", "human", "human47", "human47@gmail.com", "2930 pearl street",
            "no_image", "555-555-5555", "some notes"))
        .now();

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    when(request.getParameter("requestId")).thenReturn("14");

    when(response.getWriter()).thenReturn(writer);

    new RequestServlet().doGet(request, response);

    String rawJsonResponse = stringWriter.toString();
    assertTrue(rawJsonResponse.startsWith("{\"requestId\":14"));
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
}
