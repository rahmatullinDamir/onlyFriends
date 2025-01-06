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
    <script>
        document.addEventListener('DOMContentLoaded', () => {
            loadComments(1); // Для поста 1
            // loadComments(2); // Для поста 2
        });
    </script>
</head>
<body>
<%@include file="header.jsp" %>
<%@include file="sidebar.jsp" %>
<main class="content">
    <p>Привет ${pageContext.session.getAttribute("name")}!</p>
        <!-- Post 1 -->
        <div class="post">
            <div class="post-header">
                <div class="avatar"></div>
                <div>
                    <div class="post-author">John Doe</div>
                    <div class="post-time">2 hours ago</div>
                </div>
            </div>
            <div class="post-content">
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam sit amet augue eget nisl fermentum bibendum.
                <img src="https://via.placeholder.com/800x400" alt="Post image" class="post-image">
            </div>
            <div class="post-footer">
                <div class="like-container">
                    <button class="like-button" onclick="toggleLike(this)">&#x2764;</button>
                    <span class="like-count">0</span>
                </div>
            </div>
            <div class="comments">
                <div id="comments-list-1" class="comments-list"></div>
                <div class="comment-form">
                    <textarea id="comment-text-1" placeholder="Write a comment..."></textarea>
                    <button onclick="sendComment(1)">Post</button>
                </div>
            </div>
        </div>

        <!-- Post 2 -->
<%--        <div class="post">--%>
<%--            <div class="post-header">--%>
<%--                <div class="avatar"></div>--%>
<%--                <div>--%>
<%--                    <div class="post-author">Jane Smith</div>--%>
<%--                    <div class="post-time">5 hours ago</div>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--            <div class="post-content">--%>
<%--                Quisque venenatis velit vitae orci feugiat, sed consectetur purus consectetur.--%>
<%--                <img src="https://via.placeholder.com/800x400" alt="Post image" class="post-image">--%>
<%--                <img src="https://via.placeholder.com/800x400" alt="Post image" class="post-image">--%>
<%--                <img src="https://via.placeholder.com/800x400" alt="Post image" class="post-image">--%>
<%--            </div>--%>
<%--            <div class="post-footer">--%>
<%--                <div class="like-container">--%>
<%--                    <button class="like-button" onclick="toggleLike(this)">&#x2764;</button>--%>
<%--                    <span class="like-count">0</span>--%>
<%--                </div>--%>
<%--                <button>Comment</button>--%>
<%--            </div>--%>
<%--        </div>--%>
</main>
</body>
<script>
    function toggleLike(button) {
        const likeCountSpan = button.nextElementSibling;
        let likeCount = parseInt(likeCountSpan.textContent, 10);

        if (button.classList.contains('liked')) {
            likeCount--;
        } else {
            likeCount++;
        }

        button.classList.toggle('liked');
        likeCountSpan.textContent = likeCount;
    }

    <%--function addComment(postId) {--%>
    <%--    const textarea = document.getElementById(`comment-text-${postId}`);--%>
    <%--    const commentText = textarea.value.trim();--%>

    <%--    if (commentText) {--%>
    <%--        const commentsList = document.getElementById(`comments-list-${postId}`);--%>
    <%--        const newComment = document.createElement('div');--%>
    <%--        newComment.className = 'comment';--%>
    <%--        newComment.textContent = commentText;--%>
    <%--        commentsList.appendChild(newComment);--%>

    <%--        textarea.value = '';--%>
    <%--    }--%>
    <%--}--%>

    function sendComment(postId) {
        const commentInput = document.getElementById(`comment-text-${postId}`);
        if (!commentInput) {
            console.error(`Comment input for postId ${postId} not found`);
            return;
        }

        const commentText = commentInput.value.trim();
        if (!commentText) return;

        const encodedComment = encodeURIComponent(commentText);
        fetch(`/comment?postId=${postId}&comment=${encodedComment}`, {
            method: 'POST'
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to post comment');
                }
                return response.json();
            })
            .then(data => {
                if (data.status === 'success') {
                    commentInput.value = '';
                    loadComments(postId);
                }
            })
            .catch(error => {
                console.error('Error posting comment:', error);
            });
    }



    function loadComments(postId) {
        fetch(`/comment?postId=${postId}`)
            .then(response => response.json())
            .then(comments => {
                const commentsList = document.getElementById(`comments-list-${postId}`);
                commentsList.innerHTML = ''; // Очистить текущие комментарии
                comments.forEach(comment => {
                    const commentDiv = document.createElement('div');
                    commentDiv.className = 'comment';
                    commentDiv.textContent = comment;
                    commentsList.appendChild(commentDiv);
                });
            });
    }
</script>
<footer>
    <%@include file="footer.jsp" %>
</footer>
</html>