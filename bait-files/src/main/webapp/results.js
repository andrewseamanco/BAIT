const STAR_FILLED = '★';
const STAR_OUTLINE = '☆';
const MAX_RATING = 5;

const STAR_RATING = 'star-rating';
const AUTHENTICITY_EXPLANATION = 'authenticity-explanation';

const DOES_NOT_EXIST = 'It is likely that this person does not exist.';
const EXISTS = 'It is likely that this person exists.';

const PHONE_INPUT = 'phone-input-container';
const NAME_INPUT = 'name-input-container';
const USERNAME_INPUT = 'username-input-container';
const EMAIL_INPUT = 'email-input-container';
const IMAGE_INPUT = 'image-input-container';
const ADDRESS_INPUT = 'address-input-container';
const NOTES_INPUT = 'notes-input-container';

const PHONE_RESULTS = 'phone-results-container';
const NAME_RESULTS = 'name-results-container';
const USERNAME_RESULTS = 'username-results-container';
const EMAIL_RESULTS = 'email-results-container';
const IMAGE_RESULTS = 'image-results-container';
const ADDRESS_RESULTS = 'address-results-container';
const REVIEWER_NOTES = 'reviewer-notes-container';

const VALID = 'VALID';
const INVALID = 'INVALID';
const NOT_PROVIDED = 'This information was not provided.';

const INVALID_STRING =
    'There were some problems validating the provided information.';
const VALID_STRING =
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
        document.getElementById(NAME_INPUT)
            .appendChild(
                document.createTextNode(checkInput(result.request.name)));
        document.getElementById(EMAIL_INPUT)
            .appendChild(
                document.createTextNode(checkInput(result.request.email)));
        document.getElementById(USERNAME_INPUT)
            .appendChild(
                document.createTextNode(checkInput(result.request.username)));
        document.getElementById(PHONE_INPUT)
            .appendChild(
                document.createTextNode(checkInput(result.request.phoneNum)));
        document.getElementById(ADDRESS_INPUT)
            .appendChild(
                document.createTextNode(checkInput(result.request.address)));
        document.getElementById(IMAGE_INPUT)
            .appendChild(
                document.createTextNode(checkInput(result.request.image)));

        let stars = '';
        for (let i = 0; i < MAX_RATING; i++) {
          if (i < result.review.authenticityRating) {
            stars += STAR_FILLED;
            continue;
          }
          stars += STAR_OUTLINE;
        }

        document.getElementById(STAR_RATING)
            .appendChild(document.createTextNode(stars));

        if (result.review.authenticityRating <= 2) {
          document.getElementById(AUTHENTICITY_EXPLANATION)
              .appendChild(document.createTextNode(DOES_NOT_EXIST));
        } else {
          document.getElementById(AUTHENTICITY_EXPLANATION)
              .appendChild(document.createTextNode(EXISTS));
        }

        document.getElementById(NAME_RESULTS)
            .appendChild(document.createTextNode(
                checkValidity(result.review.nameValidity)));
        document.getElementById(EMAIL_RESULTS)
            .appendChild(document.createTextNode(
                checkValidity(result.review.emailValidity)));
        document.getElementById(USERNAME_RESULTS)
            .appendChild(document.createTextNode(
                checkValidity(result.review.usernameValidity)));
        document.getElementById(PHONE_RESULTS)
            .appendChild(
                document.createTextNode(checkValidity(result.review.phoneNum)));
        document.getElementById(ADDRESS_RESULTS)
            .appendChild(
                document.createTextNode(checkValidity(result.review.address)));
        document.getElementById(IMAGE_RESULTS)
            .appendChild(
                document.createTextNode(checkValidity(result.review.image)));

        document.getElementById(REVIEWER_NOTES)
            .appendChild(document.createTextNode(result.review.reviewerNotes));
      });
}

function checkInput(input) {
  if (input == null || input == '' || input == undefined) {
    return NOT_PROVIDED;
  } else {
    return input;
  }
}

function checkValidity(validity) {
  if (validity == VALID) {
    return VALID_STRING;
  } else {
    return INVALID_STRING;
  }
}