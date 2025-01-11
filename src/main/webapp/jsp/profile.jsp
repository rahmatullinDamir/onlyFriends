<%--
  Created by IntelliJ IDEA.
  User: damirrakhmatullin
  Date: 28.12.24
  Time: 08:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Профиль</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/profile.css">
</head>
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<body>
<%@include file="header.jsp"%>
<%@include file="sidebar.jsp"%>
<main class="content">
    <div class="profile-header">
        <img src="/<c:out value="${(avatarImageUrl)}" />" alt="User Avatar">
        <div class="info">
            <h1> <c:out value="${(name)}" /> </h1>
            <p>Возраст: <c:out value="${(age)}" /> </p>
            <p>Никнейм: <c:out value="${(username)}"/></p>
        </div>
        <c:if test="${isOwner == true}">
            <a href="${pageContext.request.contextPath}/editProfile" class="edit-profile">Редактировать профиль</a>
        </c:if>
        <c:if test="${isOwner == false}">
            <c:if test="${isFriends == 'NONE'}">
                <a href="#" class="add-friend" data-friend-id="${userProfileId}">Добавить друга</a>
            </c:if>
            <c:if test="${isFriends == 'ACCEPTED'}">
                <a href="#" class="remove-friend" data-friend-id="${userProfileId}">Удалить из друзей</a>
            </c:if>
            <c:if test="${isFriends == 'PENDING'}">
                <a href="#" class="pending-friend">Заявка отправлена!</a>
            </c:if>
        </c:if>

    </div>

    <div class="posts">
        <h2>Посты</h2>

        <c:if test="${isOwner}">
        <a href="${pageContext.request.contextPath}/addPost" class="add-post-button">
            Добавить новый пост
        </a>
        </c:if>

        <div class="posts-list">
            <c:forEach items="${posts}" var="post">
                <div class="post">
                    <div class="post-images">
                        <c:forEach items="${post.images}" var="image">
                            <img src="${pageContext.request.contextPath}/${image}" alt="postImage" class="post-image">
                        </c:forEach>
                    </div>
                    <div class="post-header">
                        <h1 class="post-title">${post.title}</h1>
                        <div class="post-date">${post.date}</div>
                        <div class="post-content">${post.text}</div>
                    </div>
                    <div class="post-footer">
                        <div class="footer-container">
                            <button class="show-comments-button" data-post-id="${post.id}">Комментарии</button>
                            <div class="like-container">
                                <button class="like-button ${post.userLiked ? 'liked' : ''}" data-post-id="${post.id}">
                                    &#x2764;
                                </button>
                                <span class="like-count">${post.likeCount}</span>
                            </div>
                        </div>
                        <div class="comments-section" id="comments-${post.id}">
                            <div class="comments-list" id="comments-list-${post.id}">
                            </div>
                            <form class="add-comment-form" data-post-id="${post.id}">
                                <input type="text" name="commentText" placeholder="Ваш коментарий...." required>
                                <button type="submit">Отправить</button>
                            </form>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
</main>
</body>
<script src="../js/likesScript.js" type="text/javascript"></script>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        const addFriendButton = document.querySelector('.add-friend');

        if (addFriendButton) {
            addFriendButton.addEventListener('click', function (event) {
                event.preventDefault();

                const friendId = this.getAttribute('data-friend-id');
                const userId = "${sessionScope.id}";

                fetch('${pageContext.request.contextPath}/addFriend', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ userId, friendId }),
                })
                    .then(response => response.json())
                    .then(data => {
                        if (data.success) {
                            alert(data.message);
                            window.location.reload()
                        } else {
                            alert("Ошибка: " + data.message);
                        }
                    })
                    .catch(error => {
                        console.error('Ошибка:', error);
                        alert("Произошла ошибка при добавлении друга.");
                    });
            });
        }
    });

    document.addEventListener('DOMContentLoaded', function () {
        const addFriendButton = document.querySelector('.remove-friend');

        if (addFriendButton) {
            addFriendButton.addEventListener('click', function (event) {
                event.preventDefault();

                const friendId = this.getAttribute('data-friend-id');
                const userId = "${sessionScope.id}";

                fetch('${pageContext.request.contextPath}/removeFriend', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ userId, friendId }),
                })
                    .then(response => response.json())
                    .then(data => {
                        if (data.success) {
                            alert(data.message);
                            window.location.reload()
                        } else {
                            alert("Ошибка: " + data.message);
                        }
                    })
                    .catch(error => {
                        console.error('Ошибка:', error);
                        alert("Произошла ошибка при удалении друга.");
                    });
            });
        }
    });

    document.addEventListener('DOMContentLoaded', function () {
        $('.show-comments-button').click(function () {
            let postId = $(this).data('post-id');

            let commentsSection = $('#comments-' + postId);

            if (commentsSection.is(':visible')) {
                commentsSection.slideUp();
            } else {
                loadComments(postId, commentsSection);
                commentsSection.slideDown();
            }
        });


        $(document).on('submit', '.add-comment-form', function (event) {
            event.preventDefault();
            let form = $(this);
            const postId = $(this).data('post-id');
            let commentText = form.find('input[name="commentText"]').val();

            addComment(postId, commentText, form);
        });


        function loadComments(postId) {
            $.ajax({
                url: '${pageContext.request.contextPath}/comments',
                method: 'POST',
                data: {
                    action: 'getComments',
                    postId: postId
                },
                success: function (comments) {
                    let commentsList = $('#comments-list-' + postId);
                    commentsList.empty();
                    comments.forEach(function (comment) {
                        commentsList.append('<p>(автор: ' + comment.author + ')<br>'+ comment.text + '</p>');
                    });
                },
                error: function () {
                    alert('Ошибка при загрузке комментариев.');
                }
            });
        }

        function addComment(postId, commentText, form) {
            $.ajax({
                url: '${pageContext.request.contextPath}/comments',
                method: 'POST',
                data: {
                    action: 'addComment',
                    postId: postId,
                    commentText: commentText,
                    authorId: "${sessionScope.id}"
                },
                success: function (response) {
                    if (response.success) {
                        form.find('input[name="commentText"]').val('');
                        loadComments(postId, $('#comments-' + postId));
                    } else {
                        alert('Ошибка при добавлении комментария.');
                    }
                },
                error: function () {
                    alert('Ошибка при добавлении комментария.');
                }
            });
        }
    });

</script>
<%@include file="footer.jsp"%>
</html>