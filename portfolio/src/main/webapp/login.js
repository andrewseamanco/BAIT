function renderLogin() {
    fetch('/login')
      .then(response => response.json())
      .then((loginHTML) => {
          console.log(loginHTML);
      });
}