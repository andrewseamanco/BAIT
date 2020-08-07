package com.google.sps.servlets;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.cloud.datastore.testing.LocalDatastoreHelper;
import com.google.sps.servlets.BlobstoreUploadServlet;
import com.google.sps.servlets.BlobstoreAccessor;
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
public final class BlobstoreUploadServletTest {
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
  public static void oneTimeTearDown() throws InterruptedException, IOException, TimeoutException
  {
    datastoreHelper.stop();
  }

  @Test
  public void doGet_createsUploadUrl() throws IOException, ServletException {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    BlobstoreAccessor blobstoreAccessor = mock(BlobstoreAccessor.class);

    when(blobstoreAccessor.createUploadUrl()).thenReturn("/testUrl");

    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    when(response.getWriter()).thenReturn(printWriter);

    BlobstoreUploadServlet uploadServlet = new BlobstoreUploadServlet(blobstoreAccessor);
    uploadServlet.doGet(request, response);

    assertTrue(stringWriter.toString().contains("/testUrl"));
  }
}