package dev.rahmatullin.controllers;

import dev.rahmatullin.repositories.PostRepository;
import dev.rahmatullin.repositories.impl.PostRepositoryJdbcImpl;
import dev.rahmatullin.services.impl.PostService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet("/feed")
public class FeedPageServlet extends HttpServlet {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "admin";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/onlyFriendsDb";

    private PostService postService;
    @Override
    public void init() throws ServletException {
        Connection connection;
        try {
            Class.forName("org.postgresql.Driver");
             connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL Driver not found", e);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
        PostRepository postRepository = new PostRepositoryJdbcImpl(connection);
        postService = new PostService(postRepository);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/feed.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
