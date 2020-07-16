package com.google.sps.servlets;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

public class Bootstrapper implements ServletContextListener {
  public void contextInitialized(ServletContextEvent event) {
    ObjectifyService.init();
    ObjectifyService.register(Request.class);
  }

  public static Objectify ofy() {
    return ObjectifyService.ofy();
  }
}