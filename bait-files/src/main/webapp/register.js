function checkFormInput() {
    const errorMessageDiv = document.getElementById('error-message-div');
    removeErrorMessage(errorMessageDiv);

    let username = document.getElementById('username-input').value;
    fetch('/usernameTaken?username=' + username)
      .then(response => response.json())
      .then((usernameTaken) => {
        if (!allFieldsFilled()) {
          printError("One or more fields are empty. Please fill out all fields to contine.", errorMessageDiv);
        } else if (usernameTaken) {
          printError("This username is already taken.  Please select a new one to continue.", errorMessageDiv);
          username = "";
        } else {
          document.getElementById("registration-form").submit();
        }
    });    
}

function removeErrorMessage(errorMessageDiv) {
    while (errorMessageDiv.lastChild) {
        errorMessageDiv.removeChild(errorMessageDiv.lastChild);
    }
}

function allFieldsFilled() {
    let firstName = document.getElementById("first-name-input");
    let lastName = document.getElementById("last-name-input");
    let username = document.getElementById("username-input");

    return !(firstName.value === "" || lastName.value === "" || username.value === "");
}

function printError(errorMessage, errorMessageDiv) {
    const errorMessageElement = document.createElement('p');
    let errorText = document.createTextNode(errorMessage);
    errorMessageElement.appendChild(errorText);
    errorMessageDiv.appendChild(errorText);
}