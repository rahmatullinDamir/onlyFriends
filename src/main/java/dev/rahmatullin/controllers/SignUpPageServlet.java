package dev.rahmatullin.controllers;

import dev.rahmatullin.dto.SignUpForm;
import dev.rahmatullin.repositories.UserRepository;
import dev.rahmatullin.repositories.impl.UserRepositoryJdbcImpl;
import dev.rahmatullin.services.SignUpService;
import dev.rahmatullin.services.impl.SignUpServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet("/signUp")
public class SignUpPageServlet extends HttpServlet {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "admin";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/onlyFriendsDb";

    private SignUpService signUpService;

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
        UserRepository userRepository = new UserRepositoryJdbcImpl(connection);
        signUpService = new SignUpServiceImpl(userRepository);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/html/signUp.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Integer age = Integer.valueOf(request.getParameter("age"));

        SignUpForm signUpForm = SignUpForm.builder()
                .name(name)
                .surname(surname)
                .email(email)
                .age(age)
                .password(password)
                .build();
        try {
            signUpService.signUp(signUpForm);
            response.sendRedirect("/signIn");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
