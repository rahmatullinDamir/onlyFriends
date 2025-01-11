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
    <title>Панель Администратора</title>
        <link rel="stylesheet" href="../static/css/admin.css">
</head>
<body>
<%@include file="header.jsp"%>
<%@include file="sidebar.jsp"%>
<main class="content">
    <h1>Администраторская панель</h1>

    <table border="1">
        <thead>
        <tr>
            <th>ID</th>
            <th>Имя пользователя</th>
            <th>Имя</th>
            <th>Фамилия</th>
            <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.name}</td>
                <td>${user.surname}</td>
                <td>
                    <button onclick="deleteUser(${user.id})">Заблокировать</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</main>
<script>
    async function deleteUser(userId) {
        if (!confirm("Вы уверены, что хотите заблокировать этого пользователя?")) {
            return;
        }

        try {
            const response = await fetch(`/admin/delete-user`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ userId }),
            });

            if (response.ok) {
                alert("Пользователь успешно заблокирован");
                location.reload();
            } else {
                alert("Ошибка при блокировке пользователя");
            }
        } catch (error) {
            console.error("Ошибка:", error);
            alert("Произошла ошибка при блокировке пользователя");
        }
    }
</script>
</body>
<%@include file="footer.jsp"%>
</html>