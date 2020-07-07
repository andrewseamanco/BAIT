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
            <button id="login-button" onclick=document.location.href="<%=UserServiceFactory.getUserService().createLoginURL("/register.jsp")%>">Start Here</button>
        </div>
        <div id="bait-image">
            <img id="login-image" src="../images/catfish_filler.jpg" />
        </div>
    </div>
  </body>
</html>