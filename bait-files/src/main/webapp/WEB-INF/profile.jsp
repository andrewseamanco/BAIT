<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Profile</title>
    <link href="https://fonts.googleapis.com/css2?family=Hind:wght@600&family=Roboto:ital@1&display=swap" rel="stylesheet">  
    <link rel="stylesheet" href="../profile.css">
    <script src="../userHistory.js"></script>
  </head>
  <body onload="fillTables()">
    <%@ page import="com.google.appengine.api.users.UserService" %>
    <%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

  <div id="profile-body">
    <section id="content">
      <nav>
        <div id="nav-title">Bait</div>
        <div id="nav-links">
          <a href="https://step-bait-project-2020.uc.r.appspot.com/requests.html" class="nav-item">Requests</a>
          <a href="https://step-bait-project-2020.uc.r.appspot.com/history.html" class="nav-item">History</a>
          <a href="<%=UserServiceFactory.getUserService().createLogoutURL("/login.jsp")%>" class="nav-item">Log Out</a>
        </div>
      </nav>

      <h3> Completed Requests </h3>

      <div id="completed-table">
          <div id="table-header">
            <div class="table-header-cell">Request Date</div>
            <div class="table-header-cell">Review</div>
          </div>
          <div id="table-body"></div>
      </div>

      <br />
      <br />

      <h3> Pending Requests </h3>

    <div id="pending-table">
        <div id="table-header">
            <div class="table-header-cell">Request Date</div>
            <div class="table-header-cell">Review</div>
          </div>
        <div id="table-body"></div>
    </div>
    </section>
  </body>
</html>