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

            <li>
                <label for="address">Address:</label><br>
                <label for="country">Country: </address>
                <select type="address" id="country-input" name="country">
                    <option value="IDK">I do not know the address</option>
                    <option value="US">United States</option>
                    <option value="CA">Canada</option>
                </select>
                <!-- To be filled by submission.js -->
                <div id="address-input-div">
                </div>
                <div id="map">
                </div>
                <p>* Check address to determine if the address fields have been typed in correctly * </p>
                <button type="button" onclick="placeMarkerOnUserInputtedAddress()">Check Address Location</button>
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
  <script src="submission.js"></script>
  <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA3dpUXSZW5eYTxffD1-atEdQeXFPB_5XM&callback=initMap"></script>
</html>