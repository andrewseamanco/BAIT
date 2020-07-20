const starfilled = '★';
const staroutline = '☆';
const maxRating = 5;

const STARRATING = 'star-rating';
const AUTHENTICITYEXPLANATION = 'authenticity-explanation';

const DOESNOTEXIST = 'It is likely that this person does not exist.';
const EXISTS = 'It is likely that this person exists.';

const PHONEINPUT = 'phone-input-container';
const NAMEINPUT = 'name-input-container';
const USERNAMEINPUT = 'username-input-container';
const EMAILINPUT = 'email-input-container';
const IMAGEINPUT = 'image-input-container';
const ADDRESSINPUT = 'address-input-container';
const NOTESINPUT = 'notes-input-container';

const PHONERESULTS = 'phone-results-container';
const NAMERESULTS = 'name-results-container';
const USERNAMERESULTS = 'username-results-container';
const EMAILRESULTS = 'email-results-container';
const IMAGERESULTS = 'image-results-container';
const ADDRESSRESULTS = 'address-results-container';
const REVIEWERNOTES = 'reviewer-notes-container';

const VALID = 'VALID';
const INVALID = 'INVALID';
const NOTAPPLICABLE = 'N/A';

const invalidString =
    'There were some problems validating the provided information.';
const validString =
    'There were no problems validating the provided information.';

function getReview() {
  const queryString = window.location.search;
  const params = new URL(location.href).searchParams;
  const requestId = params.get('reviewId');
  if (requestId == null || isNaN(requestId)) {
    window.location.replace('/');
    return;
  }

  fetch('/review' + queryString)
      .then(response => response.json())
      .then((result) => {
        document.getElementById(NAMEINPUT).appendChild(
            document.createTextNode(checkInput(result.request.name)));
        document.getElementById(EMAILINPUT)
            .appendChild(
                document.createTextNode(checkInput(result.request.email)));
        document.getElementById(USERNAMEINPUT)
            .appendChild(
                document.createTextNode(checkInput(result.request.username)));
        document.getElementById(PHONEINPUT)
            .appendChild(
                document.createTextNode(checkInput(result.request.phoneNum)));
        document.getElementById(ADDRESSINPUT)
            .appendChild(
                document.createTextNode(checkInput(result.request.address)));
        document.getElementById(IMAGEINPUT)
            .appendChild(
                document.createTextNode(checkInput(result.request.image)));

        var stars = ' '
        for (let i = 0; i < maxRating; i++) {
          if (i < result.review.authenticityRating) {
            stars += starfilled;
            continue;
          }
          stars += staroutline;
        }

        document.getElementById(STARRATING)
            .appendChild(document.createTextNode(stars));

        if (result.review.authenticityRating <= 2) {
          document.getElementById(AUTHENTICITYEXPLANATION)
              .appendChild(document.createTextNode(DOESNOTEXIST));
        } else {
          document.getElementById(AUTHENTICITYEXPLANATION)
              .appendChild(document.createTextNode(EXISTS));
        }

        document.getElementById(NAMERESULTS)
            .appendChild(document.createTextNode(
                checkValidity(result.review.nameValidity)));
        document.getElementById(EMAILRESULTS)
            .appendChild(document.createTextNode(
                checkValidity(result.review.emailValidity)));
        document.getElementById(USERNAMERESULTS)
            .appendChild(document.createTextNode(
                checkValidity(result.review.usernameValidity)));
        document.getElementById(PHONERESULTS)
            .appendChild(
                document.createTextNode(checkValidity(result.review.phoneNum)));
        document.getElementById(ADDRESSRESULTS)
            .appendChild(
                document.createTextNode(checkValidity(result.review.address)));
        document.getElementById(IMAGERESULTS)
            .appendChild(
                document.createTextNode(checkValidity(result.review.image)));

        document.getElementById(REVIEWERNOTES)
            .appendChild(document.createTextNode(result.review.reviewerNotes));
      });
}

function checkInput(input) {
  if (input == null || input == '' || input == undefined) {
    return NOTAPPLICABLE;
  } else {
    return input;
  }
}

function checkValidity(validity) {
  if (validity == VALID) {
    return validString;
  } else {
    return invalidString;
  }
}