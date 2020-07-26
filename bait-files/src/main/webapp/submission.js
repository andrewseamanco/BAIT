function fetchBlobstoreUrlAndShowForm() {
  fetch('/upload')
      .then(response => {
        return response.text();
      })
      .then((imageUploadUrl) => {
        const displayImage = document.getElementById('submission-form');
        displayImage.action = imageUploadUrl;
      });
};