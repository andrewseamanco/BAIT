var count = 0;

/** Add additional input fields for each category */
function addField(div, text) {
  const fieldDiv = document.createElement('div');
  fieldDiv.appendChild(document.createElement('br'));
  const el = document.createElement('input');

  // create image upload field for additional pictures
  if (div == 'pic-input') {
    el.type = 'file';
    el.accept = 'image/*';
  } else {
    el.type = 'text';
  }

  fieldDiv.appendChild(document.createTextNode('Additional ' + text + ': '));
  fieldDiv.appendChild(document.createElement('br'));
  fieldDiv.appendChild(el);

  // minus field button implementation
  const deleteButtonElement = document.createElement('button');
  const deleteTextNode = document.createTextNode('-');
  deleteButtonElement.appendChild(deleteTextNode);
  deleteButtonElement.addEventListener('click', () => {
    // deleteComments(request);
    fieldDiv.remove();
  });
  fieldDiv.appendChild(deleteButtonElement);
  document.getElementById(div).appendChild(fieldDiv);
}

// /** Deletes comments by ID. */
// function deleteComments(request) {
//   const params = new RequestParams();
//   params.append('id', request.id);
//   fetch('/delete-request', {method: 'POST', body: params});
// }
