<%--
  Created by IntelliJ IDEA.
  User: damirrakhmatullin
  Date: 31.12.24
  Time: 17:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Друзья</title>
    <link rel="stylesheet" href="../static/css/friends.css">
</head>
<body>
<%@include file="header.jsp"%>
<%@include file="sidebar.jsp"%>
<main class="content">
    <div class="friends-list">
        <div class="friend-card">
            <img src="https://via.placeholder.com/100" alt="Friend Avatar">
            <div class="friend-info">
                <h3>Friend Name</h3>
                <p>City: City Name</p>
            </div>
            <button class="message-button">Message</button>
        </div>
        <div class="friend-card">
            <img src="https://via.placeholder.com/100" alt="Friend Avatar">
            <div class="friend-info">
                <h3>Friend Name</h3>
                <p>City: City Name</p>
            </div>
            <button class="message-button">Message</button>
        </div>
        <!-- Add more friend cards as needed -->
    </div>
</main>
</body>
</html>
