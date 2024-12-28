<%--
  Created by IntelliJ IDEA.
  User: damirrakhmatullin
  Date: 28.12.24
  Time: 08:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>OnlyFriends</title>
    <link rel="stylesheet" href="../static/css/feed.css">
</head>
<body>
<%@include file="header.jsp" %>
<%@include file="sidebar.jsp" %>
<main class="content">
    <p>Привет ${pageContext.session.getAttribute("name")}!</p>
</main>
</body>
<footer>
    <%@include file="footer.jsp" %>
</footer>
</html>