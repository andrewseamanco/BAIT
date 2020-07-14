package com.google.sps;

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;
import static com.google.sps.data.Keys.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalUserServiceTestConfig;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import com.google.sps.servlets.UsernameTakenServlet;
import com.google.sps.servlets.LoginServlet;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class UserTest {

  private final LocalServiceTestHelper helper =
      new LocalServiceTestHelper(new LocalUserServiceTestConfig())
          .setEnvIsAdmin(true).setEnvIsLoggedIn(true).setEnvEmail("andrew").setEnvAuthDomain("gmail.com");;

  @Before
  public void setUp() {
    helper.setUp();
  }

  @After
  public void tearDown() {
    helper.tearDown();
  }

  // Database is empty, so username should not be taken
  @Test
  public void emptyUsernameTakenTest() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);    

        when(request.getParameter("username")).thenReturn("Andrew");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);
        new UsernameTakenServlet().doGet(request, response);

        verify(request, atLeast(1)).getParameter("username"); // only if you want to verify username was called...
        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().contains("false"));
  }

  // Username field is null, so return that the username is taken
  @Test
  public void nullUsernameTest() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("username")).thenReturn(null);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);
        new UsernameTakenServlet().doGet(request, response);

        verify(request, atLeast(1)).getParameter("username"); // only if you want to verify username was called...
        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().contains("true"));
  }


  // Username field is an empty string, so return that the username is taken
  @Test
  public void emptyStringUsernameTest() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("username")).thenReturn("");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);
        new UsernameTakenServlet().doGet(request, response);

        verify(request, atLeast(1)).getParameter("username"); // only if you want to verify username was called...
        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().contains("true"));
  }

  // Add a user and ensure there is a new entry in database
  @Test
  public void addUser() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);

        UserService userService = UserServiceFactory.getUserService();

        when(request.getParameter(USERNAME_ENTITY_PROPERTY)).thenReturn("Drew8521");
        when(request.getParameter(FIRST_NAME_ENTITY_PROPERTY)).thenReturn("Andrew");
        when(request.getParameter(FIRST_NAME_ENTITY_PROPERTY)).thenReturn("Seaman");

        new LoginServlet().doPost(request, response);

        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        assertEquals(1, ds.prepare(new Query(USER_ENTITY)).countEntities(withLimit(10)));
  }

    // Add a user and ensure there is a new entry in database
  @Test
  public void addUser2() throws IOException {
    UserService userService = UserServiceFactory.getUserService();
    assertTrue(userService.isUserAdmin());
  }
}