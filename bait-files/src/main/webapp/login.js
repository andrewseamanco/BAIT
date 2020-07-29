function checkFormInput() {
  const errorMessageDiv = document.getElementById('error-message-div');
  removeErrorMessage(errorMessageDiv);

  let username = document.getElementById('username-input').value;
  fetch('/usernameTaken?username=' + username)
      .then(response => response.json())
      .then((usernameTaken) => {
        if (usernameNotFilled()) {
          printError(
              'Please choose a username!',
              errorMessageDiv);
        } else if (usernameTaken) {
          printError(
              'This username is already taken.  Please select a new one to continue.',
              errorMessageDiv);
          username = '';
        } else {
          document.getElementById('registration-form').submit();
        }
      });
}

function removeErrorMessage(errorMessageDiv) {
  while (errorMessageDiv.lastChild) {
    errorMessageDiv.removeChild(errorMessageDiv.lastChild);
  }
}

function usernameNotFilled() {
  let username = document.getElementById('username-input');

  return username.value == '';
}

function printError(errorMessage, errorMessageDiv) {
  const errorMessageElement = document.createElement('p');
  let errorText = document.createTextNode(errorMessage);
  errorMessageElement.appendChild(errorText);
  errorMessageDiv.appendChild(errorText);
}