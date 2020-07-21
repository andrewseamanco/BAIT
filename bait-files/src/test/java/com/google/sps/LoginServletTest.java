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
import com.google.sps.servlets.UserAccessor;
import java.io.IOException;
import java.util.List;
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
public final class LoginServletTest {
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

//   @Before
//   public void setUp() throws InterruptedException, IOException {
//     helper.start();
//     ObjectifyFactory factory =
//         new ObjectifyFactory(helper.getOptions().getService());
//     ObjectifyService.init(factory);
//     ObjectifyService.register(User.class);
//     objectify = ObjectifyService.begin();
//   }

//   @After
//   public void tearDown() throws InterruptedException, IOException, TimeoutException {
//     objectify.close();
//     helper.stop();
//   }

  @Test
  public void doPost_newUser_hasOneuser() throws IOException, ServletException {
    // add a User object

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    UserAccessor userAccessor = mock(UserAccessor.class);

    when(userAccessor.getUserId()).thenReturn("123");
    when(request.getParameter("username")).thenReturn("Drew");
    when(request.getParameter("first-name")).thenReturn("Andrew");
    when(request.getParameter("last-name")).thenReturn("Seaman");

    StringWriter stringWriter = new StringWriter();
    PrintWriter writer = new PrintWriter(stringWriter);
    when(response.getWriter()).thenReturn(writer);

    new LoginServlet(userAccessor).doPost(request, response);

    List<User> allUsers = ObjectifyService.ofy().load().type(User.class).list();

    assertTrue(allUsers.size() == 1);

  }

}