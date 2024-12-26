<%--
  Created by IntelliJ IDEA.
  User: damirrakhmatullin
  Date: 19.12.24
  Time: 14:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Social Network Login</title>
    <link rel="stylesheet" href="../static/css/authorization.css">
</head>
<body>
<div class="login-container">
    <div class="login-box">
        <h1>Welcome Back!</h1>
        <p class="subtitle">Log in to your account</p>
        <form action="#" method="POST">
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" placeholder="Enter your email" required>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" placeholder="Enter your password" required>
            </div>
            <div class="form-group">
                <button type="submit" class="login-button">Log In</button>
            </div>
            <div class="form-links">
                <a href="#">Forgot Password?</a>
                <span> | </span>
                <a href="#">Sign Up</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>