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

      <form action="/request" method="POST">
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
              <div id="address-input-div">
                <div>
                  <label for="country">Country: </address>
                  <select type="address" id="country-code-input" name="country">
                    <option value="IDK">I do not know the address</option>
                    <option value="US">United States</option>
                    <option value="CA">Canada</option>
                  </select>
                </div>
                <div>
                  <label for="address-line-1-input">Address Line 1: </label>
                  <input type="text" id="address-line-1-input" name="address-line-1-input">
                </div>
                <div>
                  <label for="address-line-2-input">Address Line 2: </label>
                  <input type="text" id="address-line-2-input" name="address-line-2-input">
                </div>
                <div>
                  <label for="city-input">City: </label>
                  <input type="text" id="city-input" name="city-input">
                </div>
                <div>
                  <label for="state-province-input">State/Province: </address>
                  <select id="state-province-input" name="state-province-input">
                    <option value="Alabama">Alabama</option>
                    <option value="Alaska">Alaska</option>
                    <option value="Arizona">Arizona</option>
                    <option value="Arkansas">Arkansas</option>
                    <option value="California">California</option>
                    <option value="Colorado">Colorado</option>
                    <option value="Connecticut">Connecticut</option>
                    <option value="Delaware">Delaware</option>
                    <option value="Florida">Florida</option>
                    <option value="Georgia">Georgia</option>
                    <option value="Hawaii">Hawaii</option>
                    <option value="Idaho">Idaho</option>
                    <option value="Illinois">Illinois</option>
                    <option value="Indiana">Indiana</option>
                    <option value="Iowa">Iowa</option>
                    <option value="Kansas">Kansas</option>
                    <option value="Kentucky">Kentucky</option>
                    <option value="Louisiana">Louisiana</option>
                    <option value="Maine">Maine</option>
                    <option value="Maryland">Maryland</option>
                    <option value="Massachusetts">Massachusetts</option>
                    <option value="Michigan">Michigan</option>
                    <option value="Minnesota">Minnesota</option>
                    <option value="Mississippi">Mississippi</option>
                    <option value="Missouri">Missouri</option>
                    <option value="Montana">Montana</option>
                    <option value="Nebraska">Nebraska</option>
                    <option value="Nevada">Nevada</option>
                    <option value="New Hampshire">New Hampshire</option>
                    <option value="New Jersey">New Jersey</option>
                    <option value="New Mexico">New Mexico</option>
                    <option value="New York">New York</option>
                    <option value="North Carolina">North Carolina</option>
                    <option value="North Dakota">North Dakota</option>
                    <option value="Oklahoma">Oklahoma</option>
                    <option value="Ohio">Ohio</option>
                    <option value="Oregon">Oregon</option>
                    <option value="Pennsylvania">Pennsylvania</option>
                    <option value="Rhode Island">Rhode Island</option>
                    <option value="South Carolina">South Carolina</option>
                    <option value="South Dakota">South Dakota</option>
                    <option value="Tennessee">Tennessee</option>
                    <option value="Texas">Texas</option>
                    <option value="Utah">Utah</option>
                    <option value="Vermont">Vermont</option>
                    <option value="Virginia">Virginia</option>
                    <option value="Washington">Washington</option>
                    <option value="West Virginia">West Virginia</option>
                    <option value="Wisconsin">Wisconsin</option>
                    <option value="Wyoming">Wyoming</option>
                    <option value="Alberta">Alberta</option>
                    <option value="British Columbia">British Columbia</option>
                    <option value="Manitoba">Manitoba</option>
                    <option value="New Brunswick">New Brunswick</option>
                    <option value="Newfoundland and Labrador">Newfoundland and Labrador</option>
                    <option value="Northwest Territories">Northwest Territories</option>
                    <option value="Nova Scotia">Nova Scotia</option>
                    <option value="Nunavut">Nunavut</option>
                    <option value="Ontario">Ontario</option>
                    <option value="Prince Edward Island">Prince Edward Island</option>
                    <option value="Quebec">Quebec</option>
                    <option value="Saskatchewan">Saskatchewan</option>
                    <option value="Yukon">Yukon</option>
                  </select>
                </div>
                <div>
                  <label for="postal-zip-code-input">Postal/Zip Code: </label>
                  <input type="text" id="postal-zip-code-input" name="postal-zip-code-input">
                </div>
              </div>
              <div id="map">
              </div>
              <p>* Check address to determine if the address fields have been typed in correctly *</p>
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