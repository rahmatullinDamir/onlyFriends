package dev.rahmatullin.controllers;

import dev.rahmatullin.dto.PostDto;
import dev.rahmatullin.dto.UserDto;
import dev.rahmatullin.models.Image;
import dev.rahmatullin.models.Likes;
import dev.rahmatullin.models.Post;
import dev.rahmatullin.services.impl.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/profile/*")
public class ProfilePageServlet extends HttpServlet {
    private UserServiceImpl userServiceImpl;
    private ImageServiceImpl imageServiceImpl;
    private PostServiceImpl postServiceImpl;
    private FriendServiceImpl friendService;
    private LikesServiceImpl likesService;
    @Override
    public void init() throws ServletException {
        userServiceImpl = (UserServiceImpl) getServletConfig().getServletContext().getAttribute("userService");
        imageServiceImpl = (ImageServiceImpl) getServletConfig().getServletContext().getAttribute("imageService");
        postServiceImpl = (PostServiceImpl) getServletConfig().getServletContext().getAttribute("postService");
        friendService = (FriendServiceImpl) getServletConfig().getServletContext().getAttribute("friendService");
        likesService = (LikesServiceImpl) getServletConfig().getServletContext().getAttribute("likesService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String username = pathInfo.substring(1);
        try {
            UserDto user = userServiceImpl.getUserByUsername(username);
            if (user != null) {
                String curUsername = (String) request.getSession().getAttribute("username");
                Boolean isOwner = username.equals(curUsername);
                List<PostDto> posts = postServiceImpl.getPostsWithUserId(user.getId());

                for (PostDto post : posts) {
                    List<String> images = postServiceImpl.getImagesForPost(post.getId());
                    post.setImages(images);
                    post.setUserLiked(likesService.isPostLikedByUser(post.getId(),(Long)request.getSession().getAttribute("id")));
                    post.setLikeCount(likesService.getLikeCount(post.getId()));
                }

                request.setAttribute("posts", posts);
                String isFriends = "NONE";
                if (!isOwner) {
                    Long userId = (Long) request.getSession().getAttribute("id");
                    Long friendId = user.getId();
                    isFriends = friendService.isFriends(userId, friendId);
                }


                request.setAttribute("isFriends", isFriends);
                request.setAttribute("userProfileId", user.getId());
                request.setAttribute("isOwner", isOwner);
                request.setAttribute("name", user.getName() + " " + user.getSurname());
                request.setAttribute("age", user.getAge());
                request.setAttribute("username", user.getUsername());

                Image image = imageServiceImpl.getImageFromDbById(user.getAvatarImageId());

                if (user.getAvatarImageId() != 0) {
                    request.setAttribute("avatarImageUrl", image.getUrl());
                } else {
                    request.setAttribute("avatarImageUrl", image.getUrl());
                }


                request.getRequestDispatcher("/jsp/profile.jsp").forward(request, response);
            }
            else {
                request.getRequestDispatcher("/jsp/errorUserNotFound.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
