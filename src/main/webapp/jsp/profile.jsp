<%--
  Created by IntelliJ IDEA.
  User: damirrakhmatullin
  Date: 28.12.24
  Time: 08:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Профиль</title>
    <link rel="stylesheet" href="../static/css/profile.css">
</head>
<body>
<%@include file="header.jsp"%>
<%@include file="sidebar.jsp"%>
<main class="content">
    <div class="profile-header">
        <img src="https://via.placeholder.com/100" alt="User Avatar">
        <div class="info">
            <h1> <c:out value="${(name)}" default="guest" /> </h1>
            <p>Возраст: <c:out value="${(age)}" default="none" /> </p>
        </div>
        <button class="edit-profile">Edit Profile</button>
    </div>

    <div class="posts">
        <h2>User Posts</h2>
        <div class="post">
            <h3>Post Title 1</h3>
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque non diam ut ipsum suscipit mollis.</p>
        </div>
        <div class="post">
            <h3>Post Title 2</h3>
            <p>Suspendisse potenti. Nullam id dolor nec risus vestibulum posuere in nec arcu.</p>
        </div>
        <div class="post">
            <h3>Post Title 3</h3>
            <p>Morbi tristique, massa nec fermentum aliquam, purus mi vehicula elit, eu fermentum magna lorem a leo.</p>
        </div>
    </div>
</main>
</body>
<%@include file="footer.jsp"%>
</html>