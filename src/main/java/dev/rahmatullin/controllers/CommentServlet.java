package dev.rahmatullin.controllers;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.SQLException;
import java.util.*;
import com.google.gson.Gson;
import dev.rahmatullin.dto.CommentDto;
import dev.rahmatullin.models.Comment;
import dev.rahmatullin.services.CommentService;

@WebServlet("/comment")
public class CommentServlet extends HttpServlet {

    private CommentService commentService;
    @Override
    public void init() throws ServletException {
        commentService = (CommentService) getServletConfig().getServletContext().getAttribute("CommentService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long postId = Long.parseLong(request.getParameter("postId"));
        List<String> comments = new ArrayList<>();
        try {
            List<CommentDto> commentsFromDb = commentService.getCommentsByPostId(postId);
            for (CommentDto comment: commentsFromDb) {
                comments.add(comment.getText());
            }
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(comments));
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long postId = Long.parseLong(request.getParameter("postId"));
        String comment = request.getParameter("comment");
        Long userId = Long.parseLong(request.getParameter("userId"));
        try {
            commentService.saveCommentToDb(Comment.builder()
                            .postId(postId)
                            .text(comment)
                            .userId(userId)
                    .build());
            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"success\"}");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}

