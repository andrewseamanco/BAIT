package com.google.sps.servlets;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class BlobstoreAccessor {
  private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

  public String getUploadUrl() {
    blobstoreService.createUploadUrl("/request");
  }

  String serveBlobstore() {
    blobstoreService.serve(blobkey, response);
  }

  public String getUploads() {
    blobstoreService.getUploads(request);
  }

  public String loadBlobInfo() {
    BlobInfo blobInfo = new BlobInfoFactory().loadBlobInfo(blobKey);
    return blobInfo;
  }

  public String deleteBlob() {
    blobstoreService.delete(blobKey);
  }
}