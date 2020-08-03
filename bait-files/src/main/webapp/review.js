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

function initMap() {
  const address = { lat: 37.769, lng: -122.446 };
  map = new google.maps.Map(document.getElementById("address-map-view"), {
    zoom: 12,
    center: haightAshbury,
    mapTypeId: "terrain"
  });
  
  addMarker(haightAshbury);
}

function codeAddress(geocoder, map, address) {
    clearMarkers();
    geocoder.geocode({'address': address}, function(results, status) {
        if (status === 'OK') {
            map.setCenter(results[0].geometry.location);
            addMarker(results[0].geometry.location);
        } else {
            alert('Geocode was not successful for the following reason: ' + status);
        }
    });
}

(results.current_addresses.length > 0 ? results.current_addresses[0] :
                                              ''));

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
            .then((userRequest) => {
                if (userRequest.redirect) {
          alert('RequestId invalid. Redirecting to request portal.');
          window.location.replace('/admin/requests.html');
          return;
        }

        addRequestToPage(userRequest.request);
        addPhoneResultsToPage(userRequest.phoneResults);
        addEmailResultsToPage(userRequest.emailResults);
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
  addTextToPage(ADDRESS_INPUT, 'Country Code: ' + request.address.countryCode);
  addTextToPage(
      ADDRESS_INPUT, 'Address Line 1: ' + request.address.addressLine1);
  addTextToPage(
      ADDRESS_INPUT, 'Address Line 2: ' + request.address.addressLine2);
  addTextToPage(ADDRESS_INPUT, 'City: ' + request.address.city);
  addTextToPage(ADDRESS_INPUT, 'Postal Code: ' + request.address.postalCode);
  addTextToPage(ADDRESS_INPUT, 'Zip Code: ' + request.address.zipCode);
  addTextToPage(ADDRESS_INPUT, 'State: ' + request.address.state);
  addTextToPage(ADDRESS_INPUT, 'Province: ' + request.address.province);

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

  addTextToPage(PHONE_RESULTS, 'carrier: ' + results.carrier);
  addTextToPage(PHONE_RESULTS, 'country code: ' + results.country_calling_code);
  addTextToPage(PHONE_RESULTS, 'line type: ' + results.line_type);

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
  } if (text == '') {
    text = 'N/A';
  }
  let div = document.createElement('div');
  div.appendChild(document.createTextNode(text));
  document.getElementById(containerId).appendChild(div);
}



function addCurrentAddress(containerId, address) {
  if (address == '') {
    addTextToPage(containerId, 'current address: null');
    return;
  }
  addTextToPage(containerId, 'postal code: ' + address.postal_code);
  addTextToPage(containerId, 'city: ' + address.city);
  addTextToPage(containerId, 'state code: ' + address.state_code);
  addTextToPage(containerId, 'country code: ' + address.country_code);
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
