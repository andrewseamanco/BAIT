package com.google.sps.servlets;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.apache.http.impl.client.HttpClients;

public class Bootstrapper implements ServletContextListener {
  public void contextInitialized(ServletContextEvent event) {
    ObjectifyService.init();
    ObjectifyService.register(Review.class);
    ObjectifyService.register(Request.class);
    ObjectifyService.register(Url.class);
    ObjectifyService.register(User.class);
    ServletContext context = event.getServletContext();
    context.addServlet("LoginServlet", new LoginServlet(new UserAccessor()))
        .addMapping("/register");
    context
        .addServlet(
            "BlobstoreServeImageServlet", new BlobstoreServeImageServlet(new
            BlobstoreAccessor()))
        .addMapping("/blobstore-serve-image");
    context
        .addServlet("BlobstoreUploadServlet", new BlobstoreUploadServlet(new
        BlobstoreAccessor())) .addMapping("/blobstore-upload");
    context.addServlet("RequestServlet", new RequestServlet(HttpClients.createDefault()));
  }

  public static Objectify ofy() {
    return ObjectifyService.ofy();
  }
}