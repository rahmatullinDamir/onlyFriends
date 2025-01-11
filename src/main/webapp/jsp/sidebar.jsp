<%--
  Created by IntelliJ IDEA.
  User: damirrakhmatullin
  Date: 28.12.24
  Time: 08:15
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="../static/css/sidebar.css">
<aside class="sidebar">
    <ul class="menu">
        <li><a href="${pageContext.request.contextPath}/profile/${pageContext.session.getAttribute("username")}">Мой профиль</a></li>
        <li><a href="${pageContext.request.contextPath}/friends">Мои друзья</a></li>
        <c:choose>
            <c:when test="${pageContext.session.getAttribute('isAdmin')}">
               <li><a href="${pageContext.request.contextPath}/admin">Админ-Панель</a></li>
            </c:when>
        </c:choose>
        <li><a href="${pageContext.request.contextPath}/logout">Выйти</a></li>
    </ul>
</aside>
