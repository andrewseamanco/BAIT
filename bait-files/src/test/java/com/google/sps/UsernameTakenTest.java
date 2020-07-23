package com.google.sps;
  
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.cloud.datastore.testing.LocalDatastoreHelper;
import com.google.sps.servlets.User;
import com.google.sps.servlets.UsernameTakenServlet;
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
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class UsernameTakenTest {
  static LocalDatastoreHelper helper = LocalDatastoreHelper.create(1.0);

  private Closeable objectify;

  @BeforeClass
  public static void oneTimeSetUp() throws InterruptedException, IOException, TimeoutException {
      helper.start();
  }

  @Before 
  public void setUp() throws InterruptedException, IOException {
    helper.reset();
    ObjectifyFactory factory =
        new ObjectifyFactory(helper.getOptions().getService());
    ObjectifyService.init(factory);
    ObjectifyService.register(User.class);
    objectify = ObjectifyService.begin();
  }

  @After
  public void tearDown() throws InterruptedException, IOException, TimeoutException {
        objectify.close();
  }

  @AfterClass 
    public static void oneTimeTearDown() throws InterruptedException, IOException, TimeoutException {
      helper.stop();
  }

  @Test
  public void doGet_whenChosenUsernameInDb_returnsTrue() throws IOException, ServletException {
    // add a User object
    ObjectifyService.ofy()
        .save()
        .entity(new User("1234321", "Drew", "Andrew", "Seaman"))
        .now();

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    when(request.getParameter("username")).thenReturn("Drew");

    StringWriter stringWriter = new StringWriter();
    PrintWriter writer = new PrintWriter(stringWriter);
    when(response.getWriter()).thenReturn(writer);

    new UsernameTakenServlet().doGet(request, response);

    assertTrue(stringWriter.toString().contains("true"));
  }

    @Test
  public void doGet_whenDifferentUsernameInDb_returnsFalse() throws IOException, ServletException {
    // add a User object
    ObjectifyService.ofy()
        .save()
        .entity(new User("1234321", "Drew", "Andrew", "Seaman"))
        .now();

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    when(request.getParameter("username")).thenReturn("Chad");

    StringWriter stringWriter = new StringWriter();
    PrintWriter writer = new PrintWriter(stringWriter);
    when(response.getWriter()).thenReturn(writer);

    new UsernameTakenServlet().doGet(request, response);

    assertTrue(stringWriter.toString().contains("false"));
  }

  @Test
  public void doGet_nothingInDb_returnsFalse() throws IOException, ServletException {

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    when(request.getParameter("username")).thenReturn("Drew");

    StringWriter stringWriter = new StringWriter();
    PrintWriter writer = new PrintWriter(stringWriter);
    when(response.getWriter()).thenReturn(writer);

    new UsernameTakenServlet().doGet(request, response);

    assertTrue(stringWriter.toString().contains("false"));
  }

  @Test
  public void doGet_usernameIsNull_returnsTrue() throws IOException, ServletException {

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    when(request.getParameter("username")).thenReturn(null);

    StringWriter stringWriter = new StringWriter();
    PrintWriter writer = new PrintWriter(stringWriter);
    when(response.getWriter()).thenReturn(writer);

    new UsernameTakenServlet().doGet(request, response);

    assertTrue(stringWriter.toString().contains("true"));
  }

  @Test
  public void doGet_usernameIsEmptyString_returnsTrue() throws IOException, ServletException {

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    when(request.getParameter("username")).thenReturn("");

    StringWriter stringWriter = new StringWriter();
    PrintWriter writer = new PrintWriter(stringWriter);
    when(response.getWriter()).thenReturn(writer);

    new UsernameTakenServlet().doGet(request, response);

    assertTrue(stringWriter.toString().contains("true"));
  }
}
