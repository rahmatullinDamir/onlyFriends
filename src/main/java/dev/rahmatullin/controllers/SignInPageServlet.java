package dev.rahmatullin.controllers;

import dev.rahmatullin.dto.SignInForm;
import dev.rahmatullin.dto.UserDto;
import dev.rahmatullin.repositories.UserRepository;
import dev.rahmatullin.repositories.impl.UserRepositoryJdbcImpl;
import dev.rahmatullin.services.SignInService;
import dev.rahmatullin.services.impl.SignInServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@WebServlet("/signIn")
public class SignInPageServlet extends HttpServlet {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "admin";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/onlyFriendsDb";

    private SignInService signInService;
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
        signInService = new SignInServiceImpl(userRepository);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/html/signIn.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SignInForm signInForm = SignInForm.builder()
                .email(request.getParameter("email"))
                .password(request.getParameter("password"))
                .build();
        try {
            UserDto user = signInService.signIn(signInForm);

            if (user != null) {
                HttpSession httpSession = request.getSession(true);

                httpSession.setAttribute("authenticated", true);
                httpSession.setAttribute("id", user.getId());
                httpSession.setAttribute("role", user.getRole());
                httpSession.setAttribute("name", user.getName());
                httpSession.setAttribute("isAdmin", user.isAdmin());

                response.sendRedirect("/feed");
            }
            else {
                response.sendRedirect("/signIn");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
