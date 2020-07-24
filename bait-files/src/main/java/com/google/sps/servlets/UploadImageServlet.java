package com.google.sps.servlets;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * When the fetch() function requests the /blobstore-upload-url URL, the content of the response is
 * the URL that allows a user to upload a file to Blobstore. If this sounds confusing, try running a
 * dev server and navigating to /blobstore-upload-url to see the Blobstore URL.
 */
@WebServlet("/upload-image")
public class UploadImageServlet extends HttpServlet {
  private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

  //   @Override
  //   public void doPost(HttpServletRequest req, HttpServletResponse res)
  //       throws ServletException, IOException {
  // Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
  // List<BlobKey> blobKeys = blobs.get("myFile");
  // // datastore.put(blobKeys); create entity

  // if (blobKeys == null || blobKeys.isEmpty()) {
  //   res.sendRedirect("/");
  // } else {
  //   res.sendRedirect("/serve?blob-key=" + blobKeys.get(0).getKeyString());
  // }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String uploadUrl = blobstoreService.createUploadUrl("/serve-image");

    response.setContentType("text/html");
    response.getWriter().println(uploadUrl);
  }
}