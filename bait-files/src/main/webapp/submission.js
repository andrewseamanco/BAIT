/** Do not allow spaces in username input */
function keyIsValidForUsername(event) {
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

/** Only allow numbers or hypens for phone number input */
function keyIsValidForPhoneNumber(event) {
  return /[a-z, ]/i.test(event.key);
}