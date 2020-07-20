const REQUESTID = 'requestId';
const REQUESTIDCONTAINER = 'request-id';
const USERIDCONTAINER = 'user-id';
const STATUSCONTAINER = 'review-status';
const DATECONTAINER = 'submission-date';
const PHONEINPUT = 'phone-input-container';
const NAMEINPUT = 'name-input-container';
const USERNAMEINPUT = 'username-input-container';
const EMAILINPUT = 'email-input-container';
const ADDRESSINPUT = 'address-input-container';
const NOTESINPUT = 'notes-input-container';
const REVIEWREQUESTID = 'review-request-id';
const REVIEWUSERID = 'review-user-id';

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
  const requestId = params.get(REQUESTID);
  if (requestId == null || isNaN(requestId)) {
    window.location.replace('/reviews.html');
    return;
  }
  fetch('/request' + queryString)
      .then(response => response.json())
      .then((request) => {
        document.getElementById(REQUESTIDCONTAINER)
            .appendChild(document.createTextNode(request.requestId));
        document.getElementById(USERIDCONTAINER)
            .appendChild(document.createTextNode(request.userId));
        document.getElementById(STATUSCONTAINER)
            .appendChild(document.createTextNode(request.status));
        document.getElementById(DATECONTAINER)
            .appendChild(document.createTextNode(
                new Date(request.timestamp).toLocaleDateString()));
        document.getElementById(PHONEINPUT)
            .appendChild(document.createTextNode(request.phoneNum));
        document.getElementById(NAMEINPUT).appendChild(
            document.createTextNode(request.name));
        document.getElementById(USERNAMEINPUT)
            .appendChild(document.createTextNode(request.username));
        document.getElementById(EMAILINPUT)
            .appendChild(document.createTextNode(request.email));
        document.getElementById(ADDRESSINPUT)
            .appendChild(document.createTextNode(request.address));
        document.getElementById(NOTESINPUT)
            .appendChild(document.createTextNode(request.notes));
        document.getElementById(REVIEWREQUESTID).value = request.requestId;
        document.getElementById(REVIEWUSERID).value = request.userId;
      })
      .then(getPanels);
}

