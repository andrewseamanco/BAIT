<html>
  <head>
    <meta charset="UTF-8">
    <title>Results</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="results.css">
    <script src="results.js"></script>
  </head>
  <body onload="getReview()">
    <%@ page import="com.google.appengine.api.users.UserService" %>
    <%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

    <content>
      <nav>
        <div id="nav-title">Bait</div>
        <div id="nav-links">
          <a href="https://step-bait-project-2020.uc.r.appspot.com/history.jsp" class="nav-item">History</a>
          <a href="<%=UserServiceFactory.getUserService().createLogoutURL("/login.jsp")%>" class="nav-item">Log Out</a>
        </div>
      </nav>
    
      <div id="request-information" class="container">
        <div class="header">Your Request</div>
        <div id="name-input">
          <p class="sub-header">Provided Name</p>
          <div id="name-input-container" class="indent"></div>
        </div>
        <div id="email-input">
          <p class="sub-header">Provided Email</p>
          <div id="email-input-container" class="indent"></div>
        </div>
        <div id="username-input">
          <p class="sub-header">Provided Username</p>
          <div id="username-input-container" class="indent"></div>
        </div>
        <div id="phone-input">
          <p class="sub-header">Provided Phone Number</p>
          <div id="phone-input-container" class="indent"></div>
        </div>
        <div id="address-input">
          <p class="sub-header">Provided Address</p>
          <div id="address-input-container" class="indent"></div>
        </div>           
        <div id="image-input">
          <p class="sub-header">Provided Image</p>
          <div id="image-input-container" class="indent"></div>
        </div>                             
      </div>

      <div id="authenticity" class="container">
        <div class="header">Authenticity Rating</div>
        <div id="rating-container">
          <div id="star-rating"></div>
          <div id="authenticity-explanation"></div>
        </div>
      </div>

      <div id="results-container" class="container">
        <div class="header">Review Results</div>
        <div id="name-results">
          <p class="sub-header">Name</p>
          <div id="name-results-container" class="indent"></div>
        </div>
        <div id="email-results">
          <p class="sub-header">Email</p>
          <div id="email-results-container" class="indent"></div>
        </div>
        <div id="username-results">
          <p class="sub-header">Username</p>
          <div id="username-results-container" class="indent"></div>
        </div>
        <div id="phone-results">
          <p class="sub-header">Phone Number</p>
          <div id="phone-results-container" class="indent"></div>
        </div>
        <div id="address-results">
          <p class="sub-header">Address</p>
          <div id="address-results-container" class="indent"></div>
        </div>           
        <div id="image-results">
          <p class="sub-header">Image</p>
          <div id="image-results-container" class="indent"></div>
        </div> 
      </div>

      <div id="review-notes" class="container">
        <div class="header">Reviewer Notes</div>
        <p class="sub-header">Your reviewer had this to say:</p>
        <div id="reviewer-notes-container" class="indent"></div>
      </div>
    </content>
  </body>
</html>
