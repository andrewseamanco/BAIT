package com.google.sps.servlets;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.Mockito;
import com.google.cloud.datastore.testing.LocalDatastoreHelper;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.sps.servlets.BlobstoreAccessor;
import com.google.sps.servlets.BlobstoreServeImageServlet;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.concurrent.TimeoutException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
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
public final class BlobstoreServeImageServletTest {
  static LocalDatastoreHelper datastoreHelper = LocalDatastoreHelper.create(1.0);

  private Closeable objectify;

  @BeforeClass
  public static void oneTimeSetUp() throws InterruptedException, IOException, TimeoutException {
    datastoreHelper.start();
  }

  @Before
  public void setUp() throws InterruptedException, IOException {
    datastoreHelper.reset();
    ObjectifyFactory factory = new ObjectifyFactory(datastoreHelper.getOptions().getService());
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
    datastoreHelper.stop();
  }

  @Test
  public void doGet_givenBlobkey_blobstoreServes() throws IOException, ServletException {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    BlobstoreAccessor blobstoreAccessor = mock(BlobstoreAccessor.class);

    BlobKey blobKey = new BlobKey("123");
    when(request.getParameter("blobKey")).thenReturn("123");

    BlobstoreServeImageServlet serveImageServlet = new BlobstoreServeImageServlet(blobstoreAccessor);

    serveImageServlet.doGet(request, response);
    Mockito.verify(blobstoreAccessor).serve(blobKey, response);
  }
}