<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Pending Requests</title>
    <script src="requests.js"></script>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="requests.css">
  </head>
  <body onload="getRequests()">
    <%@ page import="com.google.appengine.api.users.UserService" %>
    <%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

    <content>
      <nav>
        <div id="nav-title">Bait</div>
        <div id="nav-links">
          <a href="https://step-bait-project-2020.uc.r.appspot.com/history.jsp" class="nav-item">History</a>
          <a href="<%=UserServiceFactory.getUserService().createLoginURL("/login.jsp")%>" class="nav-item">Log Out</a>
        </div>
      </nav>

      <div id="table-container">
        <p id="header">Pending Requests</p>
        <div id=table>
          <div id="table-header">
            <div class="table-header-cell">Request Id</div>
            <div class="table-header-cell">Status</div>
            <div class="table-header-cell">Request Date</div>
            <div class="table-header-cell">User Id</div>
            <div class="table-header-cell">Review</div>
          </div>
          <div id="table-body"></div>
        </div>
      </div>
    <content>
  </body>