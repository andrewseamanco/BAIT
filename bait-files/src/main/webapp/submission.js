let map;
let markers = [];

function initMap() {
  const haightAshbury = { lat: 37.769, lng: -122.446 };
  map = new google.maps.Map(document.getElementById("map"), {
    zoom: 12,
    center: haightAshbury,
    mapTypeId: 'satellite'
  });
}

// Adds a marker to the map and push to the array.
function addMarker(location) {
  const marker = new google.maps.Marker({
    position: location,
    map: map
  });
  markers.push(marker);
}

// Sets the map on all markers in the array.
function setMapOnAll(map) {
  for (let i = 0; i < markers.length; i++) {
    markers[i].setMap(map);
  }
}

// Removes the markers from the map, but keeps them in the array.
function clearMarkers() {
  setMapOnAll(null);
}

// Shows any markers currently in the array.
function showMarkers() {
  setMapOnAll(map);
}

// Deletes all markers in the array by removing references to them.
function deleteMarkers() {
  clearMarkers();
  markers = [];
}

function loadAmericanAddress() {
    const addressInputDiv = document.getElementById("address-input-div");

    //Address Line
    createTextInputElement("Address Line 1", document.getElementById("address-input-div"));
    createTextInputElement("Address Line 2", document.getElementById("address-input-div"));

    //City
    createTextInputElement("City", document.getElementById("address-input-div"));

    //State
    let stateDiv = document.createElement("div");
    stateDiv.id = "state-div";
    let stateLabel = document.createElement('label');
    stateLabel.setAttribute("for", "state-input");
    let stateLabelText = document.createTextNode("State: ");
    stateLabel.appendChild(stateLabelText);
    stateDiv.appendChild(stateLabel);
    let state = document.createElement("Select"); 
    state.setAttribute("id", "state-input"); 
    state.setAttribute("name", "state-input"); 

    createOption("Alabama", state);
    createOption("Alaska", state);
    createOption("Arizona", state);
    createOption("Arkansas", state);
    createOption("California", state);
    createOption("Colorado", state);
    createOption("Connecticut", state);
    createOption("Delaware", state);
    createOption("Florida", state);
    createOption("Georgia", state);
    createOption("Hawaii", state);
    createOption("Idaho", state);
    createOption("Illinois", state);
    createOption("Indiana", state);
    createOption("Iowa", state);
    createOption("Kansas", state);
    createOption("Kentucky", state);
    createOption("Louisiana", state);
    createOption("Maine", state);
    createOption("Maryland", state);
    createOption("Massachusetts", state);
    createOption("Michigan", state);
    createOption("Minnesota", state);
    createOption("Mississippi", state);
    createOption("Missouri", state);
    createOption("Montana", state);
    createOption("Nebraska", state);
    createOption("Nevada", state);
    createOption("New Hampshire", state);
    createOption("New Jersey", state);
    createOption("New Mexico", state);
    createOption("New York", state);
    createOption("North Carolina", state);
    createOption("North Dakota", state);
    createOption("Oklahoma", state);
    createOption("Ohio", state);
    createOption("Oregon", state);
    createOption("Pennsylvania", state);
    createOption("Rhode Island", state);
    createOption("South Carolina", state);
    createOption("South Dakota", state);
    createOption("Tennessee", state);
    createOption("Texas", state);
    createOption("Utah", state);
    createOption("Vermont", state);
    createOption("Virginia", state);
    createOption("Washington", state);
    createOption("West Virginia", state);
    createOption("Wisconsin", state);
    createOption("Wyoming", state);

    stateDiv.appendChild(state);
    addressInputDiv.appendChild(stateDiv);
    
    //Zip Code
    createTextInputElement("Zip Code", document.getElementById("address-input-div"));
}

function loadCanadianAddress() {

    //Address line
    const addressInputDiv = document.getElementById("address-input-div");

    createTextInputElement("Address Line 1", document.getElementById("address-input-div"));
    createTextInputElement("Address Line 2", document.getElementById("address-input-div"));

    //City
    createTextInputElement("City", document.getElementById("address-input-div"));

    //Province
    const provinceDiv = document.createElement("div");
    const province = document.createElement("Select"); 
    province.setAttribute("id", "province-input"); 
    province.setAttribute("name", "province-input"); 
    const provinceLabel = document.createElement('label');
    provinceLabel.setAttribute("for", "state-input");
    const provinceLabelText = document.createTextNode("Province: ");
    provinceLabel.appendChild(provinceLabelText);
    provinceDiv.appendChild(provinceLabel);

    createOption("Alberta", province);
    createOption("British Columbia", province);
    createOption("Manitoba", province);
    createOption("New Brunswick", province);
    createOption("Newfoundland and Labrador", province);
    createOption("Northwest Territories", province);
    createOption("Nova Scotia", province);
    createOption("Nunavut", province);
    createOption("Ontario", province);
    createOption("Prince Edward Island", province);
    createOption("Quebec", province);
    createOption("Saskatchewan", province);
    createOption("Yukon", province);

    provinceDiv.appendChild(province);
    addressInputDiv.appendChild(provinceDiv);

    //Postal Code
    createTextInputElement("Postal Code", document.getElementById("address-input-div"));
    
}

function createOption(optionValue, selectToAppend) {
  const option = document.createElement('option');
  option.setAttribute('value', optionValue)
  const optionText = document.createTextNode(optionValue);
  option.appendChild(optionText);
  selectToAppend.appendChild(option);
}

function createTextInputElement(inputElement, divToAppend) {
    const inputDiv = document.createElement("div");
    inputDiv.setAttribute("id", (makeIdFromInputName(inputElement) + "-div"));
    const inputLabel = document.createElement('label');
    inputLabel.setAttribute("for", (makeIdFromInputName(inputElement) + "-input"));
    const inputLabelText = document.createTextNode(inputElement + ": ");
    inputLabel.appendChild(inputLabelText);
    inputDiv.appendChild(inputLabel);
    const input = document.createElement("input");
    input.type = "text";
    input.setAttribute("id", (makeIdFromInputName(inputElement) + "-input"));
    input.setAttribute("name", (makeIdFromInputName(inputElement) + "-input"));
    inputDiv.appendChild(input);
    divToAppend.appendChild(inputDiv);
}

function placeMarkerOnUserInputtedAddress() {
    let country = document.getElementById("country-code-input").value;
        codeAddress(geocoder, map, fullAddress);
    } 
}

function makeIdFromInputName(name) {
    name = name.replace(/\s/g, "-");
    name = name.toLowerCase();
    return name;
}

function clearDiv(divToClear) {
  while (divToClear.firstChild) {
    divToClear.removeChild(divToClear.lastChild);
  }
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

const countryInput = document.getElementById("country-code-input");
countryInput.addEventListener('change', (event) => {
    const addressInputDiv = document.getElementById("address-input-div");
    clearDiv(addressInputDiv);
    if (event.target.value == "US") {
        loadAmericanAddress();
    } else if (event.target.value == "CA"){
        loadCanadianAddress();
    }
});

function placeMarkerOnUserInputtedAddress() {
    let country = document.getElementById("country-code-input").value;
    if (country == "US") {
        const addressLineOne = document.getElementById("address-line-1-input").value;
        const addressLineTwo = document.getElementById("address-line-2-input").value;
        const city = document.getElementById("city-input").value;
        const state = document.getElementById("state-input").value;
        const zip = document.getElementById("zip-code-input").value;
        const fullAddress = addressLineOne + addressLineTwo + " " + city + ", " + state + " " + zip;
        const geocoder = new google.maps.Geocoder();

        codeAddress(geocoder, map, fullAddress);
    } else if (country == "CA") {
        const addressLineOne = document.getElementById("address-line-1-input").value;
        const addressLineTwo = document.getElementById("address-line-2-input").value;
        const province = document.getElementById("province-input").value;
        const city = document.getElementById("city-input").value;
        const postal = document.getElementById("postal-code-input").value;
        const fullAddress = addressLineOne + addressLineTwo + " " + city + ", " + province + " " + postal;
        const geocoder = new google.maps.Geocoder();

        codeAddress(geocoder, map, fullAddress);
    } 
}
