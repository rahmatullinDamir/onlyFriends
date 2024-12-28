<%--
  Created by IntelliJ IDEA.
  User: damirrakhmatullin
  Date: 28.12.24
  Time: 08:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Panel</title>
        <link rel="stylesheet" href="../static/css/admin.css">
</head>
<body>
<%@include file="header.jsp"%>
<%@include file="sidebar.jsp"%>
<main class="content">
    <section class="dashboard">
        <h2>Добро пожаловать в админ-панель</h2>
        <p>Здесь вы можете управлять системой.</p>
        <div class="stats">
            <div class="stat-card">
                <h3>Пользователи</h3>
                <p>Всего: 1500</p>
            </div>
            <div class="stat-card">
                <h3>Посты</h3>
                <p>Всего: 320</p>
            </div>
            <div class="stat-card">
                <h3>Комментарии</h3>
                <p>Всего: 1050</p>
            </div>
        </div>
    </section>
</main>
</body>
<footer>
    <%@include file="footer.jsp"%>
</footer>
</html>