/** Do not allow spaces in username input */
function checkSpace(event) {
  if (event.key === ' ') {
    event.preventDefault();
    return false;
  }
  return true;
}

/** Only allow letter for name input */
function keyIsValidForName(event) {
  return /[a-z, ]/i.test(event.key);
}

/** Only allow numbers for phone number input */
function keyIsValidForPhoneNumber(event) {
  var charCode = (event.which) ? event.which : event.keyCode;
  if (charCode != 46 && charCode != 45 && charCode > 31 &&
      (charCode < 48 || charCode > 57)) {
    return false;
  }

  return true;
}