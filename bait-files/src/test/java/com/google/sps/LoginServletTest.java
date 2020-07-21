package com.google.sps;
  
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.cloud.datastore.testing.LocalDatastoreHelper;
import com.google.sps.servlets.User;
import com.google.sps.servlets.LoginServlet;
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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class UsernameTakenTest {
  LocalDatastoreHelper helper = LocalDatastoreHelper.create(1.0);

  private Closeable objectify;

  @Before
  public void setUp() throws InterruptedException, IOException {
    helper.start();
    ObjectifyFactory factory =
        new ObjectifyFactory(helper.getOptions().getService());
    ObjectifyService.init(factory);
    ObjectifyService.register(User.class);
    objectify = ObjectifyService.begin();
  }

  @After
  public void tearDown() throws InterruptedException, IOException, TimeoutException {
    objectify.close();
    helper.stop();
  }

  @Test
  public void doGet_whenChosenUsernameInDb_returnsTrue() throws IOException, ServletException {
    // add a User object

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    when(request.getParameter("username")).thenReturn("Drew");
    when(request.getParameter("first-name")).thenReturn("Andrew");
    when(request.getParameter("last-name")).thenReturn("Seaman");

    StringWriter stringWriter = new StringWriter();
    PrintWriter writer = new PrintWriter(stringWriter);
    when(response.getWriter()).thenReturn(writer);

    new LoginServlet().doGet(request, response);

    assertTrue(stringWriter.toString().contains("true"));
  }

}