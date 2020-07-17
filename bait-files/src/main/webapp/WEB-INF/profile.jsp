<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Profile</title>
    <link href="https://fonts.googleapis.com/css2?family=Hind:wght@600&family=Roboto:ital@1&display=swap" rel="stylesheet">  </head>
  <body>
    <%@ page import="com.google.appengine.api.users.UserService" %>
    <%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

    <div id="profile-body">
        <h3>Profile</h3>
        <button id="logout-button" onclick=document.location.href="<%=UserServiceFactory.getUserService().createLogoutURL("/")%>">Log Out</button>
        <div id="submission-file">
            <p>
        </div>
    </div>
  </body>
</html>