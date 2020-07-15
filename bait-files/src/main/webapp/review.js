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
      } else {
        panel.style.maxHeight = panel.scrollHeight +
            'px';  // when accordion button is clicked, if panel is not visible,
                   // set maxHeight so panel is displayed
      }
    });
  }
}

function getRequest() {
  const queryString = window.location.search;
  fetch('/request' + queryString)
      .then(response => response.json())
      .then((request) => {
        if (request.redirect) {
          window.location.replace(window.location.hostname + '/reviews.html');
          return;
        }
        document.getElementById('request-id')
            .appendChild(document.createTextNode(request.requestId));
        document.getElementById('user-id').appendChild(
            document.createTextNode(request.userId));
        document.getElementById('review-status')
            .appendChild(document.createTextNode(request.status));
        document.getElementById('submission-date')
            .appendChild(document.createTextNode(
                new Date(request.timestamp).toLocaleDateString()));
        document.getElementById('phone-input-container')
            .appendChild(document.createTextNode(request.phoneNum));
        document.getElementById('name-input-container')
            .appendChild(document.createTextNode(request.name));
        document.getElementById('username-input-container')
            .appendChild(document.createTextNode(request.username));
        document.getElementById('email-input-container')
            .appendChild(document.createTextNode(request.email));
        document.getElementById('address-input-container')
            .appendChild(document.createTextNode(request.address));
        document.getElementById('notes-input-container')
            .appendChild(document.createTextNode(request.notes));
      })
      .then(getPanels);
}