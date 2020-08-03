<html>
  <head>
    <meta charset="UTF-8">
    <title>Submission Page</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="submission.css">
    <script src="submission.js"></script>
  </head>
  <body onload="fetchBlobstoreUrl()">
    <nav>
      <div>BAIT</div>
        <div>
            <a href="https://step-bait-project-2020.uc.r.appspot.com/requests.html" class="nav-item">Requests</a>
            <a href="https://step-bait-project-2020.uc.r.appspot.com/history.html" class="nav-item">History</a>
            <a href="#" class="nav-item">Log Out</a>
        </div>
    </nav>
      
      <div id="title-text">
        <br></br>
        <h2>What do you know about this person?</h2>
        <i>Fill out as many fields as possible with the information that you have.</i>
        <br></br>
      </div>
      <form id="submission-form" method="post" enctype="multipart/form-data">
        <ul>
            <li id='name-input'>
                <label for="name">Full Name:</label><br>
                <input type="text" id="name-input" name="name-input" onkeydown="return keyIsValidForName(event)">
            </li>
            <li id='username-input'>
                <label for="username">Username:</label><br>
                <input type="text" id="username-input" name="username-input" placeholder="@" onkeypress="return checkSpace(event)">
            </li>
            <li id='email-input'>
                <label for="email">Email:</label><br>
                <input type="email" id="email-input" name="email-input" onkeydown="return checkSpace(event)">
            </li>
            <li>
              <div id="address-input-div">
                <div>
                  <label for="country">Country: </address>
                  <select type="address" id="country-code-input" name="country">
                    <option value="UNKNOWN">I don't know the address</option>
                    <option value="US">United States</option>
                    <option value="CA">Canada</option>
                  </select>
                </div>
                <div>
                  <label for="address-line-1-input">Street Line 1: </label>
                  <input type="text" id="address-line-1-input" name="address-line-1-input" placeholder="Street Address">
                </div>
                <div>
                  <label for="address-line-2-input">Street Line 2: </label>
                  <input type="text" id="address-line-2-input" name="address-line-2-input" placeholder="Apartment/Unit Number">
                </div>
                <div>
                  <label for="city-input">City: </label>
                  <input type="text" id="city-input" name="city-input">
                </div>
                <div>
                  <label for="state-province-input">State/Province: </address>
                  <select id="state-province-input" name="state-province-input">
                    <option value="AL">Alabama</option>
                    <option value="AK">Alaska</option>
                    <option value="AZ">Arizona</option>
                    <option value="AR">Arkansas</option>
                    <option value="CA">California</option>
                    <option value="CO">Colorado</option>
                    <option value="CT">Connecticut</option>
                    <option value="DE">Delaware</option>
                    <option value="DC">District of Columbia</option>
                    <option value="FL">Florida</option>
                    <option value="GA">Georgia</option>
                    <option value="HI">Hawaii</option>
                    <option value="ID">Idaho</option>
                    <option value="IL">Illinois</option>
                    <option value="IN">Indiana</option>
                    <option value="IA">Iowa</option>
                    <option value="KS">Kansas</option>
                    <option value="KY">Kentucky</option>
                    <option value="LA">Louisiana</option>
                    <option value="ME">Maine</option>
                    <option value="MD">Maryland</option>
                    <option value="MA">Massachusetts</option>
                    <option value="MI">Michigan</option>
                    <option value="MN">Minnesota</option>
                    <option value="MS">Mississippi</option>
                    <option value="MO">Missouri</option>
                    <option value="MT">Montana</option>
                    <option value="NE">Nebraska</option>
                    <option value="NV">Nevada</option>
                    <option value="NH">New Hampshire</option>
                    <option value="NJ">New Jersey</option>
                    <option value="NM">New Mexico</option>
                    <option value="NY">New York</option>
                    <option value="NC">North Carolina</option>
                    <option value="ND">North Dakota</option>
                    <option value="OH">Ohio</option>
                    <option value="OK">Oklahoma</option>
                    <option value="OR">Oregon</option>
                    <option value="PA">Pennsylvania</option>
                    <option value="RI">Rhode Island</option>
                    <option value="SC">South Carolina</option>
                    <option value="SD">South Dakota</option>
                    <option value="TN">Tennessee</option>
                    <option value="TX">Texas</option>
                    <option value="UT">Utah</option>
                    <option value="VT">Vermont</option>
                    <option value="VA">Virginia</option>
                    <option value="WA">Washington</option>
                    <option value="WV">West Virginia</option>
                    <option value="WI">Wisconsin</option>
                    <option value="WY">Wyoming</option>
                    <option value="AB">Alberta</option>
                    <option value="BC">British Columbia</option>
                    <option value="MB">Manitoba</option>
                    <option value="NB">New Brunswick</option>
                    <option value="NL">Newfoundland and Labrador</option>
                    <option value="NT">Northwest Territories</option>
                    <option value="NS">Nova Scotia</option>
                    <option value="NU">Nunavut</option>
                    <option value="ON">Ontario</option>
                    <option value="PE">Prince Edward Island</option>
                    <option value="QC">Quebec</option>
                    <option value="SK">Saskatchewan</option>
                    <option value="YT">Yukon</option>
                  </select>
                </div>
                <div>
                  <label for="postal-zip-code-input">Postal/Zip Code: </label>
                  <input type="text" id="postal-zip-code-input" name="postal-zip-code-input">
                </div>
              </div>
            </li>

            <li id='phone-input'>
                <label for="phone">Phone Number:</label><br>
                <input type="phone" id="phone-input" name="phone-input" onkeydown="return keyIsValidForPhoneNumber(event)">
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