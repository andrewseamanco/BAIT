package com.google.sps;

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalUserServiceTestConfig;
import com.google.sps.servlets.LoginServlet;
import com.google.sps.servlets.UsernameTakenServlet;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class LoginServletTest {
  private final LocalServiceTestHelper helper =
      new LocalServiceTestHelper(new LocalUserServiceTestConfig())
          .setEnvIsAdmin(true)
          .setEnvIsLoggedIn(true)
          .setEnvEmail("andrew")
          .setEnvAuthDomain("gmail.com");

  @Before
  public void setUp() {
    helper.setUp();
  }

  @After
  public void tearDown() {
    helper.tearDown();
  }

  // Add a user and ensure there is a new entry in database
  @Test
  public void doPost_forNewUser_createsUserAndRedirects() throws IOException, ServletException {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    when(request.getParameter("username")).thenReturn("Drew8521");
    when(request.getParameter("first-name")).thenReturn("Andrew");
    when(request.getParameter("last-name")).thenReturn("Seaman");

    new LoginServlet().doPost(request, response);

    DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
    assertEquals(1, ds.prepare(new Query("User")).countEntities(withLimit(10)));
    verify(response, atLeast(1)).sendRedirect("/profile.jsp");
  }
}