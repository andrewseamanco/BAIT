<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Log In</title>
    <link rel="stylesheet" href="../login.css">
    <link href="https://fonts.googleapis.com/css2?family=Hind:wght@600&family=Roboto:ital@1&display=swap" rel="stylesheet">  
    <script src="https://code.iconify.design/1/1.0.7/iconify.min.js"></script>
  </head>
  <body>
    <div id="welcome">
        <%@ page import="com.google.appengine.api.users.UserService" %>
        <%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
        <p id="logo-text">        
            <span class="iconify" data-icon="whh:bait" data-inline="false" style="color: #0277bc;" data-width="40" data-height="40"></span>
            BAIT
        </p>

        <div id="welcome-text">
            <h1 id="login-title">Your online safety starts here.</h1>
            <p id="login-subtitle">BAIT is committed to promoting authentic online interactions and ensuring the safety of those connected through the internet. </p>
            <p>The BAIT algorithm verifies the authenticity of online profiles and services to help you:</p>
            <ul id="pros-list">
                <li>
                    <img src="../images/undraw_private_data_7q35.svg" class="bait-pictures"></img>
                    Communicate with Confidence
                </li>
                <li>        
                    Verify with Ease
                    <img src="../images/undraw_modern_design_v3wv.svg" class="bait-pictures"></img>
                </li>
                <li>
                    <img src="../images/undraw_profile_6l1l.svg" class="bait-pictures"></img>
                    Make informed decsisions
                </li>
            </ul>
        </div>       
    </div>
    <div id="login">
    <h3 id="username-title">Create your username</h3>
        <% UserService userService = UserServiceFactory.getUserService();
    if (!userService.isUserLoggedIn()) { %>
    <button id="start-button" onclick=document.location.href="<%=UserServiceFactory.getUserService().createLoginURL("/register.jsp")%>">Get Started</button>
    <% } else { %>
        <div id="error-message-div"></div>
            <h3>Create Your Profile</h3>
            <form name="registration-form" id="registration-form" action="/register" method="POST">
                <ul>
                    <li>
                        <label name="username" id="username-label">Username</label> <br>
                        <input name="username" id="username-input" type="text"/>
                    </li>
                    <li>
                        <button type="button" onclick="checkFormInput()">Submit</button>
                    </li>
                </ul>
            </form>
    <% } %>
    </div>
    
    <script src="../login.js"></script>
  </body>
</html>