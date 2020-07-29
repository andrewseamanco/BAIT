<html>
  <head>
    <meta charset="UTF-8">
    <title>Submission Page</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="submission.css">
  </head>
  <body>
      <nav>
        <div id="nav-title">Bait</div>
        <div id="nav-links">
          <a href="https://step-bait-project-2020.uc.r.appspot.com/history.jsp" class="nav-item">History</a>
          <a href="#" class="nav-item">Log Out</a>
        </div>
      </nav>
      
      <div id="title-text">
        <br></br>
        <h2>What do you know about this person?</h2>
        <i>Fill out as many fields as you can with the information that you have.</i>
        <br></br>
      </div>

      <form action="/request" method="post">
        <ul>
            <li id='name-input'>
                <label for="name">Full Name:</label><br>
                <input type="text" id="name-input" name="name-input">
            </li>
            <li id='username-input'>
                <label for="username">Username:</label><br>
                <input type="text" id="username-input" name="username-input">
            </li>
            <li id='email-input'>
                <label for="email">Email:</label><br>
                <input type="email" id="email-input" name="email-input">
            </li>

            <li id='address-input'>
                <label for="address">Address:</label><br>
                <input type="address" id="address-input" name="address-input">
            </li>

            <li id='phone-input'>
                <label for="phone">Phone Number:</label><br>
                <input type="phone" id="phone-input" name="phone-input">
            </li>

            <li id='pic-input'>
                <label for="picture-input">Upload Image:</label><br>
                <input type="file" accept="image/*" id="picture-input" name="picture-input">
            </li>
            <li>
                <label for="notes-input">Additional notes:</label><br>
                <textarea rows="5" cols="50" id="notes-input" name="notes-input"></textarea> 
            </li>
            <li>
                <button type="submit" id="submit">Submit</button>
            </li>
        </ul>
      </form>
  </body>
</html>