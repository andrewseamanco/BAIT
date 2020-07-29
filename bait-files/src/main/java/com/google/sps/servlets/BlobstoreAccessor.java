package com.google.sps.servlets;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BlobstoreAccessor {
  private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

  public String createUploadUrl() {
    blobstoreService.createUploadUrl("/request");
  }

  String serve(String blobKey, HttpServletResponse response) {
    blobstoreService.serve(blobKey, response);
  }

  public String getUploads(HttpServletRequest request) {
    blobstoreService.getUploads(request);
  }

  public String loadBlobInfo(String blobKey) {
    return new BlobInfoFactory().loadBlobInfo(blobKey);
  }

  public String delete(String blobKey) {
    blobstoreService.delete(blobKey);
  }
}