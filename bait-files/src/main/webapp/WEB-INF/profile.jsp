<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Profile</title>
  </head>
  <body>

    <%@ page import="com.google.appengine.api.users.UserService" %>
    <%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

    <h1>Welcome </h1>
    <h3>Previous Searches</h3>

    <h3>Create a new search</h3>


    <button id="login-button" onclick=document.location.href="<%=UserServiceFactory.getUserService().createLogoutURL("/")%>">Log Out</button>
</html>