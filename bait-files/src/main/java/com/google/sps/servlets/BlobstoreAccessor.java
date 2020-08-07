package com.google.sps.servlets;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BlobstoreAccessor {
  private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

  public String createUploadUrl() {
    return blobstoreService.createUploadUrl("/request");
  }

  public void serve(BlobKey blobKey, HttpServletResponse response) throws IOException {
    blobstoreService.serve(blobKey, response);
  }

  public void getUploads(HttpServletRequest request) {
    blobstoreService.getUploads(request);
  }

  public BlobInfo loadBlobInfo(BlobKey blobKey) {
    return new BlobInfoFactory().loadBlobInfo(blobKey);
  }

  public void delete(BlobKey blobKey) {
    blobstoreService.delete(blobKey);
  }
}