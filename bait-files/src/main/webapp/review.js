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
const NOTES_INPUT = 'notes-input-container';
const REVIEW_REQUEST_ID = 'review-request-id';
const REVIEW_USER_ID = 'review-user-id';

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
        panel.style.border = "none";
      } else {
        panel.style.maxHeight = panel.scrollHeight +
            'px';  // when accordion button is clicked, if panel is not visible,
                   // set maxHeight so panel is displayed
        panel.style.border = "1px solid #0277bc";
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
        document.getElementById(REQUEST_ID_CONTAINER)
            .appendChild(document.createTextNode(request.requestId));
        document.getElementById(USER_ID_CONTAINER)
            .appendChild(document.createTextNode(request.userId));
        document.getElementById(STATUS_CONTAINER)
            .appendChild(document.createTextNode(request.status));
        
        document.getElementById(DATE_CONTAINER).append(document.createTextNode(new Date(request.submissionDate).toLocaleDateString() + "  " + new Date(request.submissionDate).toLocaleTimeString()));
        document.getElementById(PHONE_INPUT)
            .appendChild(document.createTextNode(request.phoneNum));
        document.getElementById(NAME_INPUT).appendChild(
            document.createTextNode(request.name));
        document.getElementById(USERNAME_INPUT)
            .appendChild(document.createTextNode(request.username));
        document.getElementById(EMAIL_INPUT)
            .appendChild(document.createTextNode(request.email));
        document.getElementById(ADDRESS_INPUT)
            .appendChild(document.createTextNode(request.address));
        document.getElementById(NOTES_INPUT)
            .appendChild(document.createTextNode(request.notes));
        document.getElementById(REVIEW_REQUEST_ID).value = request.requestId;
        document.getElementById(REVIEW_USER_ID).value = request.userId;
      })
      .then(getPanels);
}

