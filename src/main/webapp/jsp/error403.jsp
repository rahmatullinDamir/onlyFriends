<%--
  Created by IntelliJ IDEA.
  User: damirrakhmatullin
  Date: 6.01.25
  Time: 21:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang='en'>
<head>
    <meta charset='UTF-8'>
    <meta name='viewport' content='width=device-width, initial-scale=1.0'>
    <title>403 Forbidden</title>
    <link rel="stylesheet" href="/static/css/error403.css">
</head>
<body>
<h1>403 Forbidden</h1>
<p>У вас нет прав на доступ к этому ресурсу.</p>
<p>Вернуться на <a href='/profile/${sessionScope.username}'>главную страницу</a>.</p>
</body>
</html>