<%--
  Created by IntelliJ IDEA.
  User: damirrakhmatullin
  Date: 6.01.25
  Time: 22:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Добавить новый пост</title>
    <link rel="stylesheet" href="/static/css/add-post.css">
</head>
<body>
<%@include file="header.jsp"%>

<div class="content">
    <main class="add-post-container">
        <h1>Добавить новый пост</h1>
        <form id="add-post-form" action="${pageContext.request.contextPath}/addPost" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="title">Заголовок поста</label>
                <input type="text" id="title" name="title" placeholder="Введите заголовок" required>
            </div>
            <div class="form-group">
                <label for="content">Контент</label>
                <textarea id="content" name="content" rows="5" placeholder="Напишите что-нибудь..." required></textarea>
            </div>
            <div class="form-group">
                <label for="image">Изображения (необязательно)</label>
                <input type="file" id="image" name="image" accept="image/*" multiple>
            </div>
            <button type="submit" class="submit-btn">Опубликовать</button>
        </form>
    </main>
</div>
<%@include file="footer.jsp"%>
</body>
</html>

