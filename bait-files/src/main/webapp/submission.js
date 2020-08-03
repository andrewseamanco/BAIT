function fetchBlobstoreUrl() {
  fetch('/blobstore-upload')
      .then(response => {
        return response.text();
      })
      .then((imageUploadUrl) => {
        const submissionForm = document.getElementById('submission-form');
        submissionForm.action = imageUploadUrl;
      });
}