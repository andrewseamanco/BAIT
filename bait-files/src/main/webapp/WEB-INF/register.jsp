<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="login.css">
  </head>
  <body>
      <p>This is the registration.</p>
      <form action="/register" method="POST">
          <label name="first-name" id="first-name-label">First Name</label>
          <input name="first-name" id="first-name-input" type="text"/>
          <label name="last-name" id="last-name-label">Last Name</label>
          <input name="last-name" id="last-name-input" type="text"/>
          <label name="username" id="username-label">Username</label>
          <input name="username" id="username-input" type="text"/>
          <input type="submit" />
      </form>
      <p id="log"></p>
  </body>
  <script src="../register.js"></script>
</html>