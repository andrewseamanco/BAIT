<html>
  <head>
    <meta charset="UTF-8">
    <title>Review</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="review.css">
    <script src="review.js"></script>
  </head>
  <body onload="getRequest()">
    <content>
      <nav>
        <div id="nav-title">Bait</div>
        <div id="nav-links">
          <a href="https://step-bait-project-2020.uc.r.appspot.com/history.jsp" class="nav-item">History</a>
          <a href="#" class="nav-item">Log Out</a>
        </div>
      </nav>

      <section id="review-overview">
          <div id="request-id" class="overview">
            <span class="overview-item">Request Id:</span>
          </div>
          <div id="user-id" class="overview">
            <span class="overview-item">User Id:</span>
          </div>
          <div id="submission-date" class="overview">
            <span class="overview-item">Submission Date:</span>
          </div> 
          
          <div id="review-status" class="overview">
            <span class="overview-item">Status:</span>
          </div>
      </section>

      <section id="review">
        <form action="/review" method="POST">
        <input type="hidden" name="review-request-id" id="review-request-id" value="">
        <input type="hidden" name="review-user-id" id="review-user-id" value="">
        <button type="button" class="accordion">Name</button>
        <div id="name" class="panel">
          <div id="name-input">
              <p class="sub-header">Provided Name</p>
              <div id="name-input-container" class="indent"></div>
          </div>
          <div id="name-tools">
            <p class="sub-header">Name Verification Tools</p>
            <ul class="tool-list"> 
              <li>
                <a href="https://thatsthem.com/people-search" target="_blank">That's Them People Search</a>
              </li>
              <li>
                  <a href="https://www.google.com/" target="_blank">Google</a>
              </li>
            </ul>
          </div>
          <div id="name-verify" class="card-end">
            <p class="sub-header">Name Verification</p>
            <label for="name-validity" class="indent">Verify name:</label>
            <select name="name-validity" id="name-validity">
              <option></option>
              <option value="invalid">invalid</option>
              <option value="valid">valid</option>
            </select>
          </div> 
          <div class="card-end"></div> 
        </div>

        <button type="button" class="accordion">Username</button>
        <div id="username" class="panel">
          <div id="username-input">
            <p class="sub-header">Provided Username</p>
            <div id="username-input-container" class="indent"></div>
          </div>
          <div id="username-tools">
            <p class="sub-header">Username Verification Tools</p>
            <ul class="tool-list"> 
              <li>
                <a href="https://namechk.com/" target="_blank">Namechk</a>
              </li>
              <li>
                  <a href="https://www.google.com/" target="_blank">Google</a>
              </li>
            </ul>
          </div>
          <div id="username-verify" class="card-end">
            <p class="sub-header">Username Verification</p>
            <label for="username-validity" class="indent">Verify username:</label>
            <select name="username-validity" id="username-validity">
              <option></option>
              <option value="invalid">invalid</option>
              <option value="valid">valid</option>
            </select>
            <div class="card-end"></div>
          </div>
        </div> 

        <button type="button" class="accordion">Email</button>
        <div id="email" class="panel">
          <div id="email-input">
              <p class="sub-header">Provided Email</p>
              <div id="email-input-container" class="indent"></div>
          </div>
          <div id="email-api">
            <p class="sub-header">Email API Results</p>
            <div id="email-api-container" class="indent"></div>
          </div>
          <div id="email-tools">
            <p class="sub-header">Email Verification Tools</p>
            <ul class="tool-list"> 
              <li>
                <a href="https://thatsthem.com/reverse-email-lookup" target="_blank">That's Them Email Lookup</a>
              </li>
              <li>
                  <a href="https://www.google.com/" target="_blank">Google</a>
              </li>
            </ul>
          </div>
          <div id="email-verify" class="card-end">
            <p class="sub-header">Email Verification</p>
            <label for="email-validity" class="indent">Verify email:</label>
            <select name="email-validity" id="email-validity">
              <option></option>
              <option value="invalid">invalid</option>
              <option value="valid">valid</option>
            </select>
            <div class="card-end"></div>
          </div>  
        </div>
        
        <button type="button" class="accordion">Phone Number</button>
        <div id="phone" class="panel">
          <div id="phone-input">
            <p class="sub-header">Provided Phone Number</p>
            <div id="phone-input-container" class="indent"></div>
          </div>
          <div id="phone-api">
            <p class="sub-header">Phone API Results</p>
            <div id="phone-api-container" class="indent"></div>
          </div>
          <div id="phone-tools">
            <p class="sub-header">Phone Number Verification Tools</p>
            <ul class="tool-list"> 
              <li>
                <a href="https://thatsthem.com/reverse-phone-lookup" target="_blank">That's Them Phone Lookup</a>
              </li>
              <li>
                  <a href="https://www.google.com/" target="_blank">Google</a>
              </li>
            </ul>
          </div>
          <div id="phone-verify" class="card-end">
            <p class="sub-header">Phone Number Verification</p>
            <label for="phone-validity" class="indent">Verify phone number:</label>
            <select name="phone-validity" id="phone-validity">
              <option></option>
              <option value="invalid">invalid</option>
              <option value="valid">valid</option>
            </select>
            <div class="card-end"></div>
          </div>
        </div> 

          <button type="button" class="accordion">Address</button>
          <div id="address" class="panel">
            <div id="address-input">
              <p class="sub-header">Provided Address</p>
              <div id="address-input-container" class="indent"></div>
            </div>
            <div id="address-api">
              <p class="sub-header">Address API Results</p>
              <div id="address-api-container" class="indent">
                 <div id="address-map-view"></div>
              </div>
            </div>
            <div id="address-tools">
              <p class="sub-header">Address Verification Tools</p>
              <ul class="tool-list"> 
              <li>
                <a href="https://thatsthem.com/reverse-address-lookup" target="_blank">That's Them Address Lookup</a>
              </li>
              <li>
                  <a href="https://www.google.com/" target="_blank">Google</a>
              </li>
              </ul>                
            </div>
            <div id="address-verify" class="card-end">
              <p class="sub-header">Address Verification</p>
              <label for="address-validity" class="indent">Verify address:</label>
              <select name="address-validity" id="address-validity">
                <option></option>
                <option value="invalid">invalid</option>
                <option value="valid">valid</option>
              </select>            
            </div>
            <div class="card-end"></div>      
          </div> 

          <button type="button" class="accordion">Image</button>
          <div id="image" class="panel">
            <div id="image-input">
              <p class="sub-header">Provided Image</p>
              <div id="image-input-container" class="indent"></div>
            </div>
            <div id="image-api">
              <p class="sub-header">Image API Results</p>
              <div id="image-api-container" class="indent"></div>
            </div>
            <div id="image-tools">
              <p class="sub-header">Image Verification Tools</p>
              <ul class="tool-list"> 
              <li>
                <a href="https://www.google.com/imghp?hl=EN" target="_blank">Google Reverse Image Search</a>
              </li>
              <li>
                  <a href="https://www.bing.com/visualsearch/Microsoft/SimilarImages" target="_blank">Bing Visual Search</a>
              </li>
              </ul>
            </div>
            <div id="image-verify" class="card-end">
              <p class="sub-header">Image Verification</p>
              <label for="image-validity" class="indent">Verify image:</label>
                <select name="image-validity" id="image-validity">
                  <option></option>
                  <option value="invalid">invalid</option>
                  <option value="valid">valid</option>
                </select>
            <div class="card-end"></div>
            </div>  
          </div>

          <button type="button" class="accordion">Notes</button>
          <div id="notes" class="panel">
            <div id="notes-input">
              <p class="sub-header">User Notes</p>
              <div id="notes-input-container" class="indent"></div>
            </div>
            <div id="notes-review">
              <p class="sub-header">Reviewer Notes</p>
              <textarea name="reviewer-notes" id="reviewer-notes"></textarea>
            </div>
            <div class="card-end"></div>
          </div>

          <button type="button" class="accordion">Authenticity</button>
          <div id="authenticity" class="panel">
            <p class="sub-header">Authenticity Rating</p>
            <label for="authenticity-rating" class="indent">Star Rating:</label>
            <select name="authenticity-rating" id="authenticity-rating">
              <option></option>
              <option value="1">1</option>
              <option value="2">2</option>
              <option value="3">3</option>
              <option value="4">4</option>
              <option value="5">5</option>
            </select>
            <div class="card-end"></div>
          </div>

          <div class="button-container">
            <button>Submit</button>
          </div>
        </div>
        </form>
      </section>
    </content>
      <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA3dpUXSZW5eYTxffD1-atEdQeXFPB_5XM&callback=initMap"></script>
  </body>
</html>