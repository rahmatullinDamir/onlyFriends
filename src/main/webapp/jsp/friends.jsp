<%--
  Created by IntelliJ IDEA.
  User: damirrakhmatullin
  Date: 31.12.24
  Time: 17:32
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Друзья</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/friends.css">
</head>
<body>
<%@include file="header.jsp"%>
<%@include file="sidebar.jsp"%>

<main class="content">
<div class="friends-container">
    <h1>Друзья</h1>
        <div class="friends-section">
            <h2>Ваши друзья</h2>
            <ul class="friend-list">
                <c:forEach items="${friends}" var="friend">
                    <li class="friend-item">
                        <div class="friend-info">
                            <a href="/profile/${friend.username}">
                                <span class="friend-name">${friend.firstName} ${friend.lastName}</span>
                                <span class="friend-username">${friend.username}</span>
                            </a>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>

        <c:if test="${isOwner}">
            <div class="requests-section">
                <h2>Запросы на добавление в друзья</h2>
                <ul class="request-list">
                    <c:forEach items="${friendRequests}" var="request">
                        <li class="request-item">
                            <div class="request-info">
                                <span class="request-name">${request.firstName} ${request.lastName}</span>
                                <span class="request-username">${request.username}</span>
                            </div>
                            <div class="request-actions">
                                <form action="${pageContext.request.contextPath}/friends" method="post" class="accept-form">
                                    <input type="hidden" name="requestId" value="${request.id}">
                                    <input type="hidden" name="action" value="accept">
                                    <button type="submit" class="accept-button">Принять</button>
                                </form>
                                <form action="${pageContext.request.contextPath}/friends" method="post" class="reject-form">
                                    <input type="hidden" name="requestId" value="${request.id}">
                                    <input type="hidden" name="action" value="reject">
                                    <button type="submit" class="reject-button">Отклонить</button>
                                </form>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>
    </div>
</main>
</body>
<%@include file="footer.jsp"%>
</html>
