const REQUEST_ID = 'requestId';
const REQUEST_ID_CONTAINER = 'request-id';
const USER_ID_CONTAINER = 'user-id';
const STATUS_CONTAINER = 'review-status';
const DATE_CONTAINER = 'submission-date';
const PHONE_INPUT = 'phone-input-container';
const PHONE_RESULTS = 'phone-api-container'
const NAME_INPUT = 'name-input-container';
const USERNAME_INPUT = 'username-input-container';
const EMAIL_INPUT = 'email-input-container';
const EMAIL_RESULTS = 'email-api-container'
const ADDRESS_INPUT = 'address-input-container';
const IMAGE_INPUT = 'image-input-container';
const NOTES_INPUT = 'notes-input-container';
const REVIEW_REQUEST_ID = 'review-request-id';
const REVIEW_USER_ID = 'review-user-id';
const IS_VALID = 'is valid: ';
const IS_COMMERCIAL = 'is commercial: ';
const WARNING = 'Invalid Input';
const API_ERROR = 'Unable to load API results.';
const ADDRESS_RESULTS = 'address-api-container';
const POSTAL_CODE = 'postal/zip code: ';
const STATE_CODE = 'province/state: ';
const COUNTRY_CODE = 'country: ';
const CITY = 'city: ';
const STREET_LINE_1 = 'street line 1: ';
const STREET_LINE_2 = 'street line 2: ';
const NOT_VERIFIED = 'invalid';
let userAddress;


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
      .then((userRequest) => {
        if (userRequest.redirect) {
          alert('RequestId invalid. Redirecting to request portal.');
          window.location.replace('/admin/requests.html');
          return;
        }
        addRequestToPage(userRequest.request);
        
        userAddress = userRequest.request.address;
        codeAddress(getAddressString(userAddress));

        addPhoneResultsToPage(userRequest.phoneResults);
        addEmailResultsToPage(userRequest.emailResults);
        addAddressResultsToPage(userRequest.addressResults);
        if (userRequest.request.image) {
          addImageToPage(userRequest.request);
        }
      })
      .then(getPanels);
}

function addRequestToPage(request) {
  document.getElementById(REQUEST_ID_CONTAINER)
      .appendChild(document.createTextNode(request.requestId));
  document.getElementById(USER_ID_CONTAINER)
      .appendChild(document.createTextNode(request.userId));
  document.getElementById(STATUS_CONTAINER)
      .appendChild(document.createTextNode(request.status));
  document.getElementById(DATE_CONTAINER)
      .appendChild(document.createTextNode(
          new Date(request.submissionDate).toLocaleDateString() + '  ' +
          new Date(request.submissionDate).toLocaleTimeString()));
  addTextToPage(PHONE_INPUT, request.phoneNum);
  addTextToPage(NAME_INPUT, request.name);
  addTextToPage(USERNAME_INPUT, request.username);
  addTextToPage(EMAIL_INPUT, request.email);

  if (request.address.countryCode != '') {
    addTextToPage(ADDRESS_INPUT, COUNTRY_CODE + request.address.countryCode);
    addTextToPage(ADDRESS_INPUT, STREET_LINE_1 + request.address.addressLine1);
    addTextToPage(ADDRESS_INPUT, STREET_LINE_2 + request.address.addressLine2);
    addTextToPage(ADDRESS_INPUT, CITY + request.address.city);
    if (request.address.countryCode == 'US') {
      addTextToPage(ADDRESS_INPUT, 'zip code: ' + request.address.zipCode);
      addTextToPage(ADDRESS_INPUT, 'state: ' + request.address.state);
    } else if (request.address.countryCode == 'CA') {
      addTextToPage(
          ADDRESS_INPUT, 'postal code: ' + request.address.postalCode);
      addTextToPage(ADDRESS_INPUT, 'province: ' + request.address.province);
    }
  } else {
    addTextToPage(ADDRESS_INPUT, '');
  }

  addTextToPage(NOTES_INPUT, request.notes);
  document.getElementById(REVIEW_REQUEST_ID).value = request.requestId;
  document.getElementById(REVIEW_USER_ID).value = request.userId;
}


function addPhoneResultsToPage(results) {
  if (results.is_valid == undefined || results.results_unavailable ||
      results.error) {
    addTextToPage(PHONE_RESULTS, API_ERROR);
    return;
  } else if (results.warning) {
    addTextToPage(PHONE_RESULTS, WARNING);
    return;
  }

  addTextToPage(
      PHONE_RESULTS, IS_VALID + results.is_valid);
  addTextToPage(
      PHONE_RESULTS,
      (results.carrier ? 'carrier: ' + results.carrier : 'carrier: null'));
  addTextToPage(
      PHONE_RESULTS,
      (results.country_calling_code ?
           COUNTRY_CODE + results.country_calling_code :
           'country: null'));
  addTextToPage(
      PHONE_RESULTS,
      (results.line_type ? results.line_type : 'line type: null'));

  addOwner(PHONE_RESULTS, (results.belongs_to ? results.belongs_to : ''));
  addCurrentAddress(
      PHONE_RESULTS,
      (results.current_addresses.length > 0 ? results.current_addresses[0] :
                                              ''));
}

function addEmailResultsToPage(results) {
  if (results.is_valid == undefined || results.results_unavailable ||
      results.error) {
    addTextToPage(EMAIL_RESULTS, API_ERROR);
    return;
  } else if (results.warning) {
    addTextToPage(EMAIL_RESULTS, WARNING);
    return;
  }

  addTextToPage(EMAIL_RESULTS, IS_VALID + results.is_valid);
  addTextToPage(EMAIL_RESULTS, 'is autogenerated: ' + results.is_autogenerated);
  addTextToPage(EMAIL_RESULTS, 'is disposable: ' + results.is_disposable);

  addOwner(EMAIL_RESULTS, (results.belongs_to ? results.belongs_to : ''));
  addCurrentAddress(
      EMAIL_RESULTS,
      (results.current_addresses.length > 0 ? results.current_addresses[0] :
                                              ''));
}


function addAddressResultsToPage(results) {
  if (results.is_valid == undefined || results.results_unavailable ||
      results.error) {
    addTextToPage(ADDRESS_RESULTS, API_ERROR);
    return;
  }

  addTextToPage(ADDRESS_RESULTS, IS_VALID + results.is_valid);
  addResident(
      ADDRESS_RESULTS,
      (results.current_residents ? results.current_residents : ''));
  addOwner(ADDRESS_RESULTS, (results.owners ? results.owners : ''));
  addTextToPage(
      ADDRESS_RESULTS,
      (results.street_line_1 ? STREET_LINE_1 + results.street_line_1 :
                               STREET_LINE_1 + NOT_VERIFIED));
  addTextToPage(
      ADDRESS_RESULTS,
      (results.street_line_2 ? STREET_LINE_2 + results.street_line_2 :
                               STREET_LINE_2 + NOT_VERIFIED));
  addTextToPage(
      ADDRESS_RESULTS,
      (results.postal_code ? POSTAL_CODE + results.postal_code :
                             POSTAL_CODE + NOT_VERIFIED));
  addTextToPage(
      ADDRESS_RESULTS,
      (results.state_code ? STATE_CODE + results.state_code :
                            STATE_CODE + NOT_VERIFIED));
  addTextToPage(
      ADDRESS_RESULTS,
      (results.country_code ? COUNTRY_CODE + results.country_code :
                              COUNTRY_CODE + NOT_VERIFIED));
  addTextToPage(
      ADDRESS_RESULTS,
      (results.warnings.length > 0 ? 'warnings: ' + results.warnings[0] :
                                     'N/A'));
}

function addTextToPage(containerId, text) {
  if (text == '') {
    text = 'N/A';
  }
  let div = document.createElement('div');
  div.appendChild(document.createTextNode(text));
  document.getElementById(containerId).appendChild(div);
}


function addImageToPage(request) {
  let blobKeyString = request.image;
  console.log(blobKeyString);
  if (blobKeyString != undefined) {
    fetch('/blobstore-serve-image?blobKey=' + blobKeyString).then((pic) => {
      // append picture element to page
      let picture = document.createElement('img');
      picture.src = pic.url;
      document.getElementById(IMAGE_INPUT).append(picture);
    });
  }
}


function addCurrentAddress(containerId, address) {
  if (address == '') {
    addTextToPage(containerId, 'current address: null');
    return;
  }
  addTextToPage(containerId, POSTAL_CODE + address.postal_code);
  addTextToPage(containerId, CITY + address.city);
  addTextToPage(containerId, STATE_CODE + address.state_code);
  addTextToPage(containerId, COUNTRY_CODE + address.country_code);
}

function addOwner(containerId, owner) {
  if (owner == '') {
    addTextToPage(containerId, 'owner information: null');
    return;
  }
  addTextToPage(containerId, 'owner\'s name: : ' + owner.name);
  addTextToPage(containerId, 'owner\'s age range: ' + owner.age_range);
  addTextToPage(containerId, 'owner\'s gender: ' + owner.gender);
  addTextToPage(containerId, 'owner type: ' + owner.type);
}

function addResident(containerId, resident) {
  if (resident == '') {
    addTextToPage(containerId, 'resident information: null');
    return;
  }
  addTextToPage(containerId, 'resident\'s name: : ' + resident.name);
  addTextToPage(containerId, 'resident\'s age range: ' + resident.age_range);
  addTextToPage(containerId, 'resident\'s gender: ' + resident.gender);
  addTextToPage(containerId, 'resident type: ' + resident.type);
}

function getAddressString(userAddress) {
    if(userAddress.countryCode == 'CA') {
        return userAddress.countryCode + userAddress.addressLine1 + userAddress.addressLine2 + userAddress.city + userAddress.province + userAddress.postalCode;
    }
    else if(userAddress.countryCode == 'US') {
        return userAddress.countryCode + userAddress.addressLine1 + userAddress.addressLine2 + userAddress.city + userAddress.state + userAddress.postalCode;
    }

}

