<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Sign Up</title>
    <link rel="stylesheet" href="../register.css">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400&display=swap" rel="stylesheet">
  </head>
  <body>
      <h3>Create Your Profile</h3>
      <form action="/register" method="POST">
        <ul>
            <li>
                <label name="first-name" id="first-name-label">First Name</label> <br>
                <input name="first-name" id="first-name-input" type="text"/>
            </li>
            <li>
                <label name="last-name" id="last-name-label">Last Name</label> <br>
                <input name="last-name" id="last-name-input" type="text"/>
            </li>
            <li>
                <label name="username" id="username-label">Username</label> <br>
                <input name="username" id="username-input" type="text"/>
            </li>
            <li>
            <button type="submit">Submit</button>
            </li>
        </ul>
      </form>
      <p id="log"></p>
  </body>
  <script src="../register.js"></script>
</html>