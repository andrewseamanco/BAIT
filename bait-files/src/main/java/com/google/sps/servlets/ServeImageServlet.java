package com.google.sps.servlets;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * When the user submits the form, Blobstore processes the file upload and then forwards the request
 * to this servlet. This servlet can then process the request using the file URL we get from
 * Blobstore.
 */
@WebServlet("/serve-image")
public class ServeImageServlet extends HttpServlet {
  private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
  private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
  //   @Override
  //   public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
  //     BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));
  //     blobstoreService.serve(blobKey, res);
  //   }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get the message entered by the user.
    // String message = request.getParameter("message");

    // Get the URL of the image that the user uploaded to Blobstore.
    // String imageUrl = getUploadedFileUrl(request, "picture-input");
    // System.out.print(imageUrl);

    // Output some HTML that shows the data the user entered.
    // A real codebase would probably store these in Datastore.
    // PrintWriter out = response.getWriter();
    // out.println("<p>Here's the image you uploaded:</p>");
    // out.println("<a href=\"" + imageUrl + "\">");
    // out.println("<img src=\"" + imageUrl + "\" />");
    // out.println("</a>");
    // out.println("<p>Here's the text you entered:</p>");
    // out.println(message);


        String imageUrl = getUploadedFileUrl(request, "picture-input");
  }

  /** Returns a URL that points to the uploaded file, or null if the user didn't upload a file. */
  private String getUploadedFileUrl(HttpServletRequest request, String formInputElementName) {
    // BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
    List<BlobKey> blobKeys = blobs.get("formInputElementName");

    // User submitted form without selecting a file, so we can't get a URL. (dev server)
    if (blobKeys == null || blobKeys.isEmpty()) {
      return null;
    }

    // Our form only contains a single file input, so get the first index.
    BlobKey blobKey = blobKeys.get(0);

    // User submitted form without selecting a file, so we can't get a URL. (live server)
    BlobInfo blobInfo = new BlobInfoFactory().loadBlobInfo(blobKey);
    if (blobInfo.getSize() == 0) {
      blobstoreService.delete(blobKey);
      return null;
    }

    // Checks that the file is the right type.
    if (!blobInfo.getContentType().contains("picture-input")) {
      blobstoreService.delete(blobKey);
      throw new IllegalArgumentException("Please upload an image of a valid file type.");
    }

    // System.out.print(blobKey.getKeyString());
    return blobKey.getKeyString();

    // We could check the validity of the file here, e.g. to make sure it's an image file
    // https://stackoverflow.com/q/10779564/873165

    // Use ImagesService to get a URL that points to the uploaded file.
    // ImagesService imagesService = ImagesServiceFactory.getImagesService();
    // ServingUrlOptions options = ServingUrlOptions.Builder.withBlobKey(blobKey);

    // To support running in Google Cloud Shell with AppEngine's devserver, we must use the relative
    // path to the image, rather than the path returned by imagesService which contains a host.
    // try {
    //   URL url = new URL(imagesService.getServingUrl(options));
    //   return url.getPath();
    // } catch (MalformedURLException e) {
    //   return imagesService.getServingUrl(options);
    // }
  }
}
