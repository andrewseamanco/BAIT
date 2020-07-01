const input = document.getElementById('username-input');
const log = document.getElementById('log');
input.addEventListener('input', updateValue);

function updateValue(e) {
  log.textContent = e.target.value;
}