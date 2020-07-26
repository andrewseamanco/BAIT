// function fetchBlobstoreUrlAndShowForm() {
//   fetch('/serve-image')
//       .then(response => response.text())
//       .then(imageUploadUrl => {
//         //   console.log(imageUploadUrl);
//         fetch('/get-image?blobkey=' + imageUploadUrl).then(pic => {
//           const displayImage = document.getElementById('blobstore-test');

//           // if no image uploaded
//           if (blobkey != 'null') {
//             displayImage.action = imageUploadUrl;
//             let image = document.createElement('img');
//             image.src = imageUploadUrl;
//             displayImage.append(image);
//           }
//         });
//       });
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

function fetchBlobstoreUrlAndShowForm() {
  fetch('/upload')
      .then(response => {
        return response.text();
      })
      .then((imageUploadUrl) => {
        const displayImage = document.getElementById('submission-form');
        displayImage.action = imageUploadUrl;
      });
}

// function fetchBlobstoreUrlAndShowForm() {
//   fetch('/upload').then(response => response.text()).then(imageUploadUrl => {
//     console.log(imageUploadUrl);
//     console.log(imageUploadUrl.substring(
//         imageUploadUrl.indexOf('upload/') + 7, imageUploadUrl.length));
//     var blobkey = imageUploadUrl.substring(
//         imageUploadUrl.indexOf('upload/') + 7, imageUploadUrl.length);
//     fetch('/get-image?blobkey=' + blobkey).then(pic => {
//       const listEl = document.getElementById('pic-input');

//       const inputEl = document.getElementById('picture-input');

//       console.log(pic);
//       // if no image uploaded
//       //   if (blobkey != 'null') {
//       inputEl.action = imageUploadUrl;
//       let image = document.createElement('img');
//       image.src = imageUploadUrl;
//       listEl.append(image);
//       //   }
//     });
//   });
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
