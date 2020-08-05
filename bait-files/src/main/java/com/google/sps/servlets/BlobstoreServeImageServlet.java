package com.googl.sps.servlets;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.sps.servlets.BlobstoreAccessor;
import com.google.appengine.api.blobstore.BlobKey;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Serves the image file corresponding to the given blob key
 */
@WebServlet("/blobstore-serve-image")
public class BlobstoreServeImageServlet extends HttpServlet {
  private final BlobstoreAccessor blobstoreAccessor;

//   public BlobstoreServeImageServlet() {
//     this.blobstoreAccessor = new BlobstoreAccessor();
//   }

  public BlobstoreServeImageServlet(BlobstoreAccessor blobstoreAccessor) {
    this.blobstoreAccessor = blobstoreAccessor;
  }

  /**
   * Gets a URL that can dynamically serve the image stored as a blob by passing the blobkey to
   * blobstore service
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    BlobKey blobKey = new BlobKey(request.getParameter("blobKey"));
    blobstoreAccessor.serve(blobKey, response);
  }
}
