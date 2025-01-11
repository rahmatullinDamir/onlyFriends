<%--
  Created by IntelliJ IDEA.
  User: damirrakhmatullin
  Date: 28.12.24
  Time: 06:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="../static/css/header.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100..900;1,100..900&display=swap" rel="stylesheet">
<header class="header">
    <nav class="nav">
        <a class="home" href="${pageContext.request.contextPath}/">Only Friends</a>
        <form class="search-form" action="${pageContext.request.contextPath}/search" method="get">
            <input type="text" name="query" class="search-input" placeholder="Поиск по никнейму">
            <button type="submit" class="search-button">🔍</button>
        </form>
    </nav>
</header>
