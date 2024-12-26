<%--
  Created by IntelliJ IDEA.
  User: damirrakhmatullin
  Date: 19.12.24
  Time: 15:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Social Network Registration</title>
    <link rel="stylesheet" href="../static/css/registration.css">
</head>
<body>
<div class="registration-container">
    <div class="registration-box">
        <h1>Create Your Account</h1>
        <p class="subtitle">Join our social network</p>
        <form action="#" method="POST">
            <div class="form-group">
                <label for="fullname">Full Name</label>
                <input type="text" id="fullname" name="fullname" placeholder="Enter your full name" required>
            </div>
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" placeholder="Enter your email" required>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" placeholder="Enter your password" required>
            </div>
            <div class="form-group">
                <label for="confirm-password">Confirm Password</label>
                <input type="password" id="confirm-password" name="confirm-password" placeholder="Confirm your password" required>
            </div>
            <div class="form-group">
                <button type="submit" class="register-button">Register</button>
            </div>
            <div class="form-links">
                <a href="#">Already have an account? Log In</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>
