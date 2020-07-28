function fetchBlobstoreUrlAndShowImage() {
  fetch('/blobstore-upload')
      .then(response => {
        return response.text();
      })
      .then((imageUploadUrl) => {
        const displayImage = document.getElementById('submission-form');
        displayImage.action = imageUploadUrl;
      });
}
