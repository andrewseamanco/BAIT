<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Profile</title>
  </head>
  <body>
      <h1>Hello</h1>
      <p>This is the profile.</p>

    <%@ page import="com.google.appengine.api.users.UserService" %>
    <%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

      <p>Please <a href="<%= UserServiceFactory.getUserService().createLogoutURL("/") %>">Logout</a></p>
</html>