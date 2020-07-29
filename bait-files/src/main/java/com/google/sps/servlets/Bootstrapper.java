package com.google.sps.servlets;
import com.google.sps.servlets.UserAccessor;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

public class Bootstrapper implements ServletContextListener {
  public void contextInitialized(ServletContextEvent event) {
    ObjectifyService.init();
    ObjectifyService.register(Review.class);
    ObjectifyService.register(Request.class);
    ObjectifyService.register(User.class);
    ServletContext context = event.getServletContext();
    context.addServlet("LoginServlet", new LoginServlet(new UserAccessor()))
        .addMapping("/register");
    context
        .addServlet(
            "BlobstoreServeImageServlet", new BlobstoreServeImageServlet(new BlobstoreAccessor()))
        .addMapping("/blobstore-get-image");
    context
        .addServlet("BlobstoreUploadServlet", new BlobstoreUploadServlet(new BlobstoreAccessor()))
        .addMapping("/blobstore-upload");
  }

  public static Objectify ofy() {
    return ObjectifyService.ofy();
  }
}