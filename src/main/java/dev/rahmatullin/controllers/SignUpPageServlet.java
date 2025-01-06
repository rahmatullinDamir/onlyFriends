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
    private SignUpService signUpService;

    @Override
    public void init() throws ServletException {
        signUpService = (SignUpService) getServletConfig().getServletContext().getAttribute("signUpService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/static/html/signUp.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Integer age = Integer.valueOf(request.getParameter("age"));
        String username = request.getParameter("username");

        SignUpForm signUpForm = SignUpForm.builder()
                .name(name)
                .surname(surname)
                .email(email)
                .age(age)
                .password(password)
                .username(username)
                .build();
        try {
            signUpService.signUp(signUpForm);
            response.sendRedirect("/signIn");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
