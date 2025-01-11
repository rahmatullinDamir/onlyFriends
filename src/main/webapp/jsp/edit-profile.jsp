<%--
  Created by IntelliJ IDEA.
  User: damirrakhmatullin
  Date: 6.01.25
  Time: 21:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Изменить профиль</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/edit-profile.css">
</head>
<body>
<div class="profile-container">
    <h1>Редактировать профиль</h1>
    <form class="profile-form" action="${pageContext.request.contextPath}/updateProfile" method="post" enctype="multipart/form-data">

        <div class="avatar-section">
            <img src="https://via.placeholder.com/300" alt="User Avatar" class="avatar-preview">
            <input type="file" name="avatar" id="avatar" accept="image/*">
            <label for="avatar" class="avatar-label">Измените аватарку</label>
        </div>
        <div class="form-group">
            <label for="name">Имя</label>
            <input type="text" id="name" name="name" placeholder="Измените имя" required>
        </div>
        <div class="form-group">
            <label for="surname">Фамилия</label>
            <input type="text" id="surname" name="surname" placeholder="Измените фамилию " required>
        </div>
        <div class="form-group">
            <label for="age">Возраст (16 - 100)</label>
            <input type="number" id="age" name="age" min="16" max="100" required >
        </div>
        <button type="submit" class="save-button">Сохранить изменения</button>
    </form>
</div>
</body>
</html>
