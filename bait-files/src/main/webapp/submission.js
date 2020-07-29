/** Do not allow spaces in username input */
function keyIsValidForUsername(event) {
  if (event.key === ' ') {
    event.preventDefault();
    return false;
  }
  return true;
}
