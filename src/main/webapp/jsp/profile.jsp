<%--
  Created by IntelliJ IDEA.
  User: damirrakhmatullin
  Date: 31.12.24
  Time: 15:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Профиль пользователя</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/profile.css">
</head>
<body>
<%@include file="header.jsp"%>
<div class="container">
    <%@include file="sidebar.jsp"%>
    <main class="main-content">
        <div class="profile">
            <div class="profile-header">
                <img src="avatar.jpg" alt="Аватар" class="avatar">
                <div class="profile-info">
                    <h1 class="name">Имя Фамилия</h1>
                    <p class="status">Статус пользователя</p>
                    <button class="add-friend-btn">Добавить в друзья</button>
                </div>
            </div>
            <div class="profile-body">
                <div class="left-column">
                    <div class="about">
                        <h2>О себе</h2>
                        <p>Возраст: 25</p>
                        <p>Город: Москва</p>
                        <p>Интересы: Музыка, спорт, программирование</p>
                    </div>
                </div>
                <div class="right-column">
                    <div class="posts">
                        <h2>Записи</h2>
                        <div class="post">
                            <p>Сегодня был отличный день! :)</p>
                            <span class="date">31.12.2024</span>
                        </div>
                        <div class="post">
                            <p>Учусь делать сайты.</p>
                            <span class="date">30.12.2024</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
</div>
</body>
<%@include file="footer.jsp"%>
</html>
