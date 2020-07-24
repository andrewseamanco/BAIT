

function fetchBlobstoreUrlAndShowForm() {
  fetch('/serve-image').then(response => response.text()).then(imageUploadUrl => {
    //   console.log(imageUploadUrl);
        fetch('/get-image?blobkey=' + imageUploadUrl).then(pic => {
          const displayImage = document.getElementById('blobstore-test');

        //if no image uploaded
          if (blobkey != 'null') {
          displayImage.action = imageUploadUrl;
          let image = document.createElement('img');
          image.src = imageUploadUrl;
          displayImage.append(image);
          }
        });
      });
}

// function createImageElement(pic) {
//   let image = document.createElement('img');
//   image.src = pic;
//   image.id = 'player-picture';
//   const imageContainer = document.getElementById('image-container');
//   imageContainer.append(image);
// }

// function fetchBlobstoreUrlAndShowForm() {
//   fetch('/upload-image')
//       .then(response => {
//         return response.text();
//       })
//       .then((imageUploadUrl) => {
//         const displayImage = document.getElementById('blobstore-test');
//         displayImage.action = imageUploadUrl;
//         let image = document.createElement('img');
//         image.src = imageUploadUrl;
//         // image.id = 'blobstore-test';
//         displayImage.append(image);
//       });
// }

// function fetchBlobstoreUrlAndShowForm() {
//   fetch('/upload-image')
//       .then((response) => {
//         return response.text();
//       })
//       .then((imageUploadUrl) => {
//         const form = document.getElementById('submission-form');
//         form.action = imageUploadUrl;
//         // messageForm.classList.remove('hidden');
//       });
// }
