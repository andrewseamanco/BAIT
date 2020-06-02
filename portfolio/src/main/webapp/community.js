function renderComments() {
  fetch('/subtraction-game').then(response => response.json()).then((game) => {