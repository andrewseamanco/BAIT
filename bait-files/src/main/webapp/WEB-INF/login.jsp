<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Log In</title>
    <link rel="stylesheet" href="../login.css">
    <link href="https://fonts.googleapis.com/css2?family=Hind:wght@600&family=Roboto:ital@1&display=swap" rel="stylesheet">  </head>
  <body>
    <%@ page import="com.google.appengine.api.users.UserService" %>
    <%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

    <div id="login-body">
        <div id="bait-mission">
            <h1 id="login-title">Your online safety starts here.</h1>
            <h3 id="login-subtitle">BAIT is committed to promoting authentic interactions online and ensuring the safety of those connected through the internet.
                The BAIT algorithm verifies the authenticity of online profiles and services and helps you make informed decisions when
                online.
            </h3>
            <% 
            UserService userService = UserServiceFactory.getUserService();
            if (!userService.isUserLoggedIn()) { %>
                <button id="login-button" onclick=document.location.href="<%=UserServiceFactory.getUserService().createLoginURL("/register.jsp")%>">Start Here</button>
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
        <div id="bait-image">
            <img id="login-image" src="../images/catfish_filler.jpg" />
        </div>
    </div>
    <script src="../login.js"></script>
  </body>
</html>