<%--
  Created by IntelliJ IDEA.
  User: damirrakhmatullin
  Date: 6.01.25
  Time: 21:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Пользователь не найден</title>
    <link rel="stylesheet" href="/static/css/user-not-found.css">
</head>
<body>
<div class="container">
    <h1>Пользователь с таким никнеймом не найден</h1>
    <p>Попробуйте другой никнейм.</p>
    <a href="/profile/${sessionScope.username}">Вернуться на главную страницу</a>
</div>
</body>
</html>

