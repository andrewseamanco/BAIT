function loadAmericanAddress() {
    //Address

    let addressInputDiv = document.getElementById("address-input-div");

    let addressDiv = document.createElement("div");
    addressDiv.id = "address-div";
    let addressLabel = document.createElement('label');
    addressLabel.setAttribute("for", "address-line-input");
    let addressLabelText = document.createTextNode("Address: ");
    addressLabel.appendChild(addressLabelText);
    addressDiv.appendChild(addressLabel);
    let address = document.createElement("input");
    address.type = "text";
    addressDiv.appendChild(address);
    addressInputDiv.appendChild(addressDiv);

    let cityDiv = document.createElement("div");
    let cityLabel = document.createElement('label');
    cityLabel.setAttribute("for", "city-input");
    let cityLabelText = document.createTextNode("City: ");
    cityLabel.appendChild(cityLabelText);
    cityDiv.appendChild(cityLabel);
    let city = document.createElement("input");
    city.type = "text";
    cityDiv.appendChild(city);
    addressInputDiv.appendChild(cityDiv);

    
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
    
    let zipDiv = document.createElement("div");
    zipDiv.id = "city-div";
    let zipLabel = document.createElement('label');
    zipLabel.setAttribute("for", "zip-input");
    let zipLabelText = document.createTextNode("Zip Code: ");
    zipLabel.appendChild(zipLabelText);
    zipDiv.appendChild(zipLabel);
    let zip = document.createElement("input");
    zip.type = "text";
    zipDiv.appendChild(zip);
    addressInputDiv.appendChild(zipDiv);
}

function loadCanadianAddress() {
    //Address line
    let addressInputDiv = document.getElementById("address-input-div");

    let addressDiv = document.createElement("div");
    addressDiv.id = "address-div";
    let addressLabel = document.createElement('label');
    addressLabel.setAttribute("for", "address-line-input");
    let addressLabelText = document.createTextNode("Address: ");
    addressLabel.appendChild(addressLabelText);
    addressDiv.appendChild(addressLabel);
    let address = document.createElement("input");
    address.type = "text";
    address.setAttribute("name", "state-input"); 
    addressDiv.appendChild(address);
    addressInputDiv.appendChild(addressDiv);

    //Province

    let provinceDiv = document.createElement("div");
    let province = document.createElement("Select"); 
    province.setAttribute("id", "province-input"); 
    province.setAttribute("name", "province-input"); 
    let provinceLabel = document.createElement('label');
    provinceLabel.setAttribute("for", "state-input");
    let provinceLabelText = document.createTextNode("Province: ");
    provinceLabel.appendChild(provinceLabelText);
    provinceDiv.appendChild(provinceLabel);

    createOption("Alberta", province);
    createOption("British Columbia", province);
    createOption("Manitoba", province);
    createOption("Alberta", province);
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

    //City
    let cityDiv = document.createElement("div");
    cityDiv.id = "city-div";
    let cityLabel = document.createElement('label');
    cityLabel.setAttribute("for", "city-input");
    let cityLabelText = document.createTextNode("City: ");
    cityLabel.appendChild(cityLabelText);
    cityDiv.appendChild(cityLabel);
    let city = document.createElement("input");
    city.type = "text";
    city.setAttribute("name", "city-input"); 
    cityDiv.appendChild(city);
    addressInputDiv.appendChild(cityDiv);

    //Postal Code
    let postalDiv = document.createElement("div");
    postalDiv.id = "city-div";
    let postalLabel = document.createElement('label');
    postalLabel.setAttribute("for", "postal-input");
    let postalLabelText = document.createTextNode("Postal Code: ");
    postalLabel.appendChild(postalLabelText);
    postalDiv.appendChild(postalLabel);
    let postal = document.createElement("input");
    postal.type = "text";
    postal.setAttribute("name", "postal-input"); 
    postalDiv.appendChild(postal);
    addressInputDiv.appendChild(postalDiv);
    
}

function createOption(optionValue, selectToAppend) {
    let option = document.createElement("option");
    option.setAttribute("value", optionValue)
    let optionText = document.createTextNode(optionValue);
    option.appendChild(optionText);
    selectToAppend.appendChild(option);
}

function clearDiv(divToClear) {
  while (divToClear.firstChild) {
    divToClear.removeChild(divToClear.lastChild);
  }
}

let countryInput = document.getElementById("country-input");
countryInput.addEventListener('change', (event) => {
    console.log(event.target.value);
    let addressInputDiv = document.getElementById("address-input-div");
    clearDiv(addressInputDiv);
    if (event.target.value == "US") {
        loadAmericanAddress();
    } else if (event.target.value == "CA"){
        loadCanadianAddress();
    }
});