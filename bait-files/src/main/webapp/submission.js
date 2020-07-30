function loadAmericanAddress() {
  const addressInputDiv = document.getElementById('address-input-div');

  // Address Line
  createTextInputElement(
      'Address Line 1', document.getElementById('address-input-div'));
  createTextInputElement(
      'Address Line 2', document.getElementById('address-input-div'));

  // City
  createTextInputElement('City', document.getElementById('address-input-div'));

  // State
  const stateDiv = document.createElement('div');
  stateDiv.id = 'state-div';
  const stateLabel = document.createElement('label');
  stateLabel.setAttribute('for', 'state-input');
  const stateLabelText = document.createTextNode('State: ');
  stateLabel.appendChild(stateLabelText);
  stateDiv.appendChild(stateLabel);
  const state = document.createElement('Select');
  state.setAttribute('id', 'state-input');
  state.setAttribute('name', 'state-input');

  createOption('Alabama', state);
  createOption('Alaska', state);
  createOption('Arizona', state);
  createOption('Arkansas', state);
  createOption('California', state);
  createOption('Colorado', state);
  createOption('Connecticut', state);
  createOption('Delaware', state);
  createOption('Florida', state);
  createOption('Georgia', state);
  createOption('Hawaii', state);
  createOption('Idaho', state);
  createOption('Illinois', state);
  createOption('Indiana', state);
  createOption('Iowa', state);
  createOption('Kansas', state);
  createOption('Kentucky', state);
  createOption('Louisiana', state);
  createOption('Maine', state);
  createOption('Maryland', state);
  createOption('Massachusetts', state);
  createOption('Michigan', state);
  createOption('Minnesota', state);
  createOption('Mississippi', state);
  createOption('Missouri', state);
  createOption('Montana', state);
  createOption('Nebraska', state);
  createOption('Nevada', state);
  createOption('New Hampshire', state);
  createOption('New Jersey', state);
  createOption('New Mexico', state);
  createOption('New York', state);
  createOption('North Carolina', state);
  createOption('North Dakota', state);
  createOption('Oklahoma', state);
  createOption('Ohio', state);
  createOption('Oregon', state);
  createOption('Pennsylvania', state);
  createOption('Rhode Island', state);
  createOption('South Carolina', state);
  createOption('South Dakota', state);
  createOption('Tennessee', state);
  createOption('Texas', state);
  createOption('Utah', state);
  createOption('Vermont', state);
  createOption('Virginia', state);
  createOption('Washington', state);
  createOption('West Virginia', state);
  createOption('Wisconsin', state);
  createOption('Wyoming', state);

  stateDiv.appendChild(state);
  addressInputDiv.appendChild(stateDiv);

  // Zip Code
  createTextInputElement(
      'Postal', document.getElementById('address-input-div'));
}

function loadCanadianAddress() {
  // Address line
  const addressInputDiv = document.getElementById('address-input-div');

  createTextInputElement(
      'Address Line 1', document.getElementById('address-input-div'));
  createTextInputElement(
      'Address Line 2', document.getElementById('address-input-div'));

  // City
  createTextInputElement('City', document.getElementById('address-input-div'));

  // Province
  const provinceDiv = document.createElement('div');
  const province = document.createElement('Select');
  province.setAttribute('id', 'province-input');
  province.setAttribute('name', 'province-input');
  const provinceLabel = document.createElement('label');
  provinceLabel.setAttribute('for', 'state-input');
  const provinceLabelText = document.createTextNode('Province: ');
  provinceLabel.appendChild(provinceLabelText);
  provinceDiv.appendChild(provinceLabel);

  createOption('Alberta', province);
  createOption('British Columbia', province);
  createOption('Manitoba', province);
  createOption('New Brunswick', province);
  createOption('Newfoundland and Labrador', province);
  createOption('Northwest Territories', province);
  createOption('Nova Scotia', province);
  createOption('Nunavut', province);
  createOption('Ontario', province);
  createOption('Prince Edward Island', province);
  createOption('Quebec', province);
  createOption('Saskatchewan', province);
  createOption('Yukon', province);

  provinceDiv.appendChild(province);
  addressInputDiv.appendChild(provinceDiv);

  // Postal Code
  createTextInputElement(
      'Postal', document.getElementById('address-input-div'));
}

function createOption(optionValue, selectToAppend) {
  const option = document.createElement('option');
  option.setAttribute('value', optionValue)
  const optionText = document.createTextNode(optionValue);
  option.appendChild(optionText);
  selectToAppend.appendChild(option);
}

function createTextInputElement(inputElement, divToAppend) {
  const inputDiv = document.createElement('div');
  inputDiv.setAttribute('id', (inputElement + '-div'));
  const inputLabel = document.createElement('label');
  inputLabel.setAttribute('for', (inputElement + '-label'));
  const inputLabelText = document.createTextNode(inputElement + ': ');
  inputLabel.appendChild(inputLabelText);
  inputDiv.appendChild(inputLabel);
  const input = document.createElement('input');
  input.type = 'text';
  inputDiv.appendChild(input);
  divToAppend.appendChild(inputDiv);
}

function clearDiv(divToClear) {
  while (divToClear.firstChild) {
    divToClear.removeChild(divToClear.lastChild);
  }
}

const countryInput = document.getElementById('country-input');
countryInput.addEventListener('change', (event) => {
  const addressInputDiv = document.getElementById('address-input-div');
  clearDiv(addressInputDiv);
  if (event.target.value == 'US') {
    loadAmericanAddress();
  } else if (event.target.value == 'CA') {
    loadCanadianAddress();
  }
});