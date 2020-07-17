package com.google.sps.servlets;

import static junit.framework.Assert.assertNotNull;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.sps.servlets.Enums.Validity;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.cache.AsyncCacheFilter;
import com.googlecode.objectify.cmd.Query;
import com.googlecode.objectify.util.Closeable;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReviewServletTest {
  private final LocalServiceTestHelper helper =
      new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

  protected Closeable session;

  @BeforeClass
  public static void setUpBeforeClass() {
    ObjectifyService.init(new ObjectifyFactory());
    ObjectifyService.register(Review.class);
  }

  @Before
  public void setUp() {
    this.session = ObjectifyService.begin();
    this.helper.setUp();
  }

  @After
  public void tearDown() {
    AsyncCacheFilter.complete();
    this.session.close();
    this.helper.tearDown();
  }

  @Test
  public void doTest() {
    Review review = new Review(null, "0000", "1234", Validity.VALID, Validity.VALID, Validity.VALID,
        Validity.VALID, Validity.VALID, Validity.VALID, 3, "Hallo");

    ObjectifyService.ofy().save().entity(review).now();

    Query<Review> query = ObjectifyService.ofy().load().type(Review.class);
    List<Review> reviews = query.list();
    System.out.println(reviews);

    assertNotNull(reviews);
  }
}