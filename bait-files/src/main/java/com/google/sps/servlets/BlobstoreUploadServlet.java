package com.google.sps.servlets;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.sps.servlets.BlobstoreAccessor;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The fetch() function in submission.js requests the /blobstore-upload URL, the response is
 * the URL that allows a user to upload a file to Blobstore
 */
@WebServlet("/blobstore-upload")
public class BlobstoreUploadServlet extends HttpServlet {
  private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

  public BlobstoreUploadServlet(BlobstoreAccessor blobstoreAccessor) {
    this.blobstoreAccessor = blobstoreAccessor;
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String uploadUrl = blobstoreService.createUploadUrl("/request");

    response.setContentType("text/html");
    response.getWriter().println(uploadUrl);
  }
}