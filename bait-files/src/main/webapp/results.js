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
        addDataToPage(NAME_INPUT, getInputString(result.request.name));
        addDataToPage(EMAIL_INPUT, getInputString(result.request.email));
        addDataToPage(USERNAME_INPUT, getInputString(result.request.username));
        addDataToPage(PHONE_INPUT, getInputString(result.request.phoneNum));
        if (result.request.address.countryCode != 'IDK') {
          addDataToPage(
              ADDRESS_INPUT,
              'Country Code: ' + result.request.address.countryCode);
          addDataToPage(
              ADDRESS_INPUT,
              'Address Line 1: ' + result.request.address.addressLine1);
          addDataToPage(
              ADDRESS_INPUT,
              'Address Line 2: ' + result.request.address.addressLine2);
          addDataToPage(ADDRESS_INPUT, 'City: ' + result.request.address.city);
          if (result.request.address.countryCode == 'US') {
            addDataToPage(
                ADDRESS_INPUT, 'Zip Code: ' + result.request.address.zipCode);
            addDataToPage(
                ADDRESS_INPUT, 'State: ' + result.request.address.state);
          } else if (result.request.address.countryCode == 'CA') {
            addDataToPage(
                ADDRESS_INPUT,
                'Postal Code: ' + result.request.address.postalCode);
            addDataToPage(
                ADDRESS_INPUT, 'Province: ' + result.request.address.province);
          }
        }

        addDataToPage(IMAGE_INPUT, getInputString(result.request.image));
        addDataToPage(
            REVIEWER_NOTES, getInputString(result.review.reviewerNotes));

        let starRatingString =
            STAR_FILLED.repeat(result.review.authenticityRating)
                .concat(STAR_OUTLINE.repeat(
                    MAX_RATING - result.review.authenticityRating));

        addDataToPage(STAR_RATING, starRatingString);

        if (result.review.authenticityRating <= 2) {
          addDataToPage(AUTHENTICITY_EXPLANATION, DOES_NOT_EXIST);
        } else {
          addDataToPage(AUTHENTICITY_EXPLANATION, EXISTS);
        }

        addDataToPage(
            NAME_RESULTS, getValidityString(result.review.nameValidity));
        addDataToPage(
            EMAIL_RESULTS, getValidityString(result.review.emailValidity));
        addDataToPage(
            USERNAME_RESULTS,
            getValidityString(result.review.usernameValidity));
        addDataToPage(
            PHONE_RESULTS, getValidityString(result.review.phoneNumValidity));
        addDataToPage(
            ADDRESS_RESULTS, getValidityString(result.review.addressValidity));
        addDataToPage(
            IMAGE_RESULTS, getValidityString(result.review.imageValidity));
      });
}

function getInputString(input) {
  if (input == null || input == '' || input == undefined) {
    return NOT_PROVIDED;
  } else {
    return input;
  }
}

function getValidityString(validity) {
  if (validity == VALID) {
    return VALID_STRING;
  } else {
    return INVALID_STRING;
  }
}

function addDataToPage(container, data) {
  let div = document.createElement('div');
  div.appendChild(document.createTextNode(data));
  document.getElementById(container).appendChild(div);
}