<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Request History</title>
    <link href="https://fonts.googleapis.com/css2?family=Hind:wght@600&family=Roboto:ital@1&display=swap" rel="stylesheet">  
    <link rel="stylesheet" href="../history.css">
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
          <a href="https://step-bait-project-2020.uc.r.appspot.com/history.jsp" class="nav-item">History</a>
          <a href="<%=UserServiceFactory.getUserService().createLogoutURL("/login.jsp")%>" class="nav-item">Log Out</a>
        </div>
      </nav>

      <h3> Completed Reviews </h3>
      <div id="completed-table" class="table">
          <div id="table-header">
            <div class="table-header-cell">Request Date</div>
            <div class="table-header-cell">Request Id</div>
            <div class="table-header-cell">Results</div>
          </div>
          <div id="table-body"></div>
      </div>


    <h3> Pending Reviews </h3>
    <div id="pending-table" class="table">
        <div id="table-header">
            <div class="table-header-cell">Request Date</div>
            <div class="table-header-cell">Request Id</div>
            <div class="table-header-cell">Results</div>
          </div>
        <div id="table-body"></div>
    </div>
    </section>

    <div id="new-request">
        <h3>Create a New Request</h3>
        <a href="https://step-bait-project-2020.uc.r.appspot.com/submission.html"><button>+</button></a>
    </div>
  </body>
</html>