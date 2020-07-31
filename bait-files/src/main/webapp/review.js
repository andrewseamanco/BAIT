const REQUEST_ID = 'requestId';
const REQUEST_ID_CONTAINER = 'request-id';
const USER_ID_CONTAINER = 'user-id';
const STATUS_CONTAINER = 'review-status';
const DATE_CONTAINER = 'submission-date';
const PHONE_INPUT = 'phone-input-container';
const NAME_INPUT = 'name-input-container';
const USERNAME_INPUT = 'username-input-container';
const EMAIL_INPUT = 'email-input-container';
const ADDRESS_INPUT = 'address-input-container';
const IMAGE_INPUT = 'image-input-container';
const NOTES_INPUT = 'notes-input-container';
const REVIEW_REQUEST_ID = 'review-request-id';
const REVIEW_USER_ID = 'review-user-id';
const ERROR_MESSAGE =
    'This review is invalid. Please check all inputs and resubmit.';

function getPanels() {
  const accordion = document.getElementsByClassName('accordion');
  for (fold of accordion) {
    fold.addEventListener('click', function() {
      this.classList.toggle('active');
      const panel = this.nextElementSibling;
      if (panel.style.maxHeight) {  // when accordion button is clicked, if
                                    // panel is visible closes panel by setting
                                    // maxHeight to null
        panel.style.maxHeight = null;
        panel.style.border = 'none';
      } else {
        panel.style.maxHeight = panel.scrollHeight +
            'px';  // when accordion button is clicked, if panel is not visible,
                   // set maxHeight so panel is displayed
        panel.style.border = '1px solid #0277bc';
      }
    });
  }
}

function getRequest() {
  const queryString = window.location.search;
  const params = new URL(location.href).searchParams;
  const requestId = params.get(REQUEST_ID);
  if (requestId == null || isNaN(requestId)) {
    window.location.replace('/requests.html');
    return;
  }

  fetch('/request' + queryString)
      .then(response => response.json())
      .then((request) => {
        addRequestToPage(request);
        addImageToPage(request);
        if (request.redirect) {
          alert('RequestId invalid. Redirecting to request portal.');
          window.location.replace('/requests.html');
          return;
        }
      })
      .then(getPanels);
}

function addRequestToPage(request) {
  addTextToPage(REQUEST_ID_CONTAINER, request.requestId);
  addTextToPage(USER_ID_CONTAINER, request.userId);
  addTextToPage(STATUS_CONTAINER, request.status);
  addTextToPage(
      DATE_CONTAINER,
      new Date(request.submissionDate).toLocaleDateString() + '  ' +
          new Date(request.submissionDate).toLocaleTimeString());
  addTextToPage(PHONE_INPUT, request.phoneNum);
  addTextToPage(NAME_INPUT, request.name);
  addTextToPage(USERNAME_INPUT, request.username);
  addTextToPage(EMAIL_INPUT, request.email);
  addTextToPage(ADDRESS_INPUT, request.address);
  addTextToPage(NOTES_INPUT, request.notes);
  document.getElementById(REVIEW_REQUEST_ID).value = request.requestId;
  document.getElementById(REVIEW_USER_ID).value = request.userId;
}

function addTextToPage(containerId, text) {
  document.getElementById(containerId)
      .appendChild(document.createTextNode(text));
}

function addImageToPage(request) {
  let blobKeyString = request.image;
  if (blobKeyString != undefined) {
    fetch('/blobstore-serve-image?blobKey=' + blobKeyString).then((pic) => {
      // append picture element to page
      let picture = document.createElement('img');
      picture.src = pic.url;
      document.getElementById(IMAGE_INPUT).append(picture);
    });
  }
}
