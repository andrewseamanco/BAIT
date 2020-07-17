package com.google.sps;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.cache.AsyncCacheFilter;
import com.googlecode.objectify.util.Closeable;
import static com.google.sps.data.Constants.*;
import com.google.sps.servlets.User;
import com.google.sps.servlets.LoginServlet;
import javax.servlet.ServletException;
import java.util.List;

import static org.mockito.Mockito.*;
import java.io.*;
import javax.servlet.http.*;

import org.junit.*;
import static junit.framework.Assert.*;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static java.util.stream.Collectors.toList;

@RunWith(JUnit4.class)
public final class LoginTest {

    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig())
                .setEnvIsAdmin(true).setEnvIsLoggedIn(true).setEnvEmail("Andrew").setEnvAuthDomain("gmail.com");
    
    protected Closeable session;

    @Before
    public void setUp() {
        this.helper.setUp();
        ObjectifyService.init();
        ObjectifyService.register(User.class);
        this.session = ObjectifyService.begin();
    }

    @After
    public void tearDown() {
        AsyncCacheFilter.complete();
        this.session.close();
        this.helper.tearDown();
    }  

@Test
  public void containsPoint() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter(USERNAME_PARAMETER)).thenReturn("Drew8521");
        when(request.getParameter(FIRST_NAME_PARAMETER)).thenReturn("Andrew");
        when(request.getParameter(LAST_NAME_PARAMETER)).thenReturn("Seaman");

        when(request.getParameter("is-test")).thenReturn("true");

        new LoginServlet().doPost(request, response);

        verify(request, atLeast(1)).getParameter(USERNAME_PARAMETER); 
        verify(request, atLeast(1)).getParameter(FIRST_NAME_PARAMETER); 
        verify(request, atLeast(1)).getParameter(LAST_NAME_PARAMETER); 

        List<User> allUsers = ObjectifyService.ofy().load().type(User.class).list();

  }

}