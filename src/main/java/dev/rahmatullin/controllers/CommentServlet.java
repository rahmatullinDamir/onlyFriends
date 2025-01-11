package dev.rahmatullin.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.SQLException;
import java.util.*;

import com.google.gson.Gson;
import dev.rahmatullin.dto.CommentDto;
import dev.rahmatullin.dto.UserDto;
import dev.rahmatullin.models.Comment;
import dev.rahmatullin.services.CommentService;
import dev.rahmatullin.services.UserService;

@WebServlet("/comments")
public class CommentServlet extends HttpServlet {

    private CommentService commentService;
    private UserService userService;
    private Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        commentService = (CommentService) getServletConfig().getServletContext().getAttribute("commentService");
        userService = (UserService) getServletConfig().getServletContext().getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        if ("getComments".equals(action)) {
            Long postId = Long.parseLong(request.getParameter("postId"));

            try {
                List<CommentDto> comments = commentService.getCommentsByPostId(postId);
                for (CommentDto comment : comments) {
                    UserDto author = userService.getUserById(comment.getUserId());
                    comment.setAuthor(author.getUsername());
                }
                response.setContentType("application/json");
                response.getWriter().write(gson.toJson(comments));
            } catch (SQLException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка при загрузке комментариев.");
            }
        } else if ("addComment".equals(action)) {
            Long postId = Long.parseLong(request.getParameter("postId"));
            String commentText = request.getParameter("commentText");
            Long authorId = Long.parseLong(request.getParameter("authorId"));
            String author = "";

            try {
                UserDto user = userService.getUserById(authorId);
                author = user.getName() + " " + user.getSurname();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                Comment newComment = Comment.builder()
                        .text(commentText)
                        .postId(postId)
                        .userId(authorId)
                        .author(author)
                        .build();
                System.out.println(newComment);
                commentService.saveCommentToDb(newComment);
                response.setContentType("application/json");
                response.getWriter().write(gson.toJson(Map.of("success", true)));
            } catch (SQLException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка при добавлении комментария.");
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action");
        }
    }
}
