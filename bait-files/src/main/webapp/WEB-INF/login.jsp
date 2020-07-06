<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Login</title>
  </head>
  <body>
      <h1>Welcome to Bait</h1>

    <%@ page import="com.google.appengine.api.users.UserService" %>
    <%@ page import="com.google.appengine.api.users.UserServiceFactory" %>


    <p>Please <a href="<%= UserServiceFactory.getUserService().createLoginURL("/register.jsp") %>">Login</a></p>
    
  </body>
</html>