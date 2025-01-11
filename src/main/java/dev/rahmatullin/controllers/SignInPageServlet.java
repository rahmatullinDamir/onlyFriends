package dev.rahmatullin.controllers;

import dev.rahmatullin.dto.SignInForm;
import dev.rahmatullin.dto.UserDto;
import dev.rahmatullin.services.SignInService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/signIn")
public class SignInPageServlet extends HttpServlet {
    private SignInService signInService;
    @Override
    public void init() throws ServletException {
        signInService = (SignInService) getServletConfig().getServletContext().getAttribute("signInService");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/static/html/signIn.html").forward(request, response);
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
                httpSession.setAttribute("username", user.getUsername());
                httpSession.setAttribute("isAdmin", user.isAdmin());

                response.sendRedirect("/profile/" + user.getUsername());
            }
            else {
                response.sendRedirect("/signIn");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
