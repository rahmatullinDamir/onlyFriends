package dev.rahmatullin.controllers;

import com.google.gson.Gson;
import dev.rahmatullin.dto.SignUpForm;
import dev.rahmatullin.services.SignUpService;
import dev.rahmatullin.services.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/signUp")
public class SignUpPageServlet extends HttpServlet {
    private SignUpService signUpService;
    private UserServiceImpl userService;
    @Override
    public void init() throws ServletException {
        signUpService = (SignUpService) getServletConfig().getServletContext().getAttribute("signUpService");
        userService = (UserServiceImpl) getServletConfig().getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/static/html/signUp.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            jsonBuilder.append(line);
        }
        String json = jsonBuilder.toString();

        Gson gson = new Gson();
        SignUpForm signUpFromJson = gson.fromJson(json, SignUpForm.class);


        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {

            boolean isEmailTaken = userService.isEmailTaken(signUpFromJson.getEmail());
            boolean isUsernameTaken = userService.isUsernameTaken(signUpFromJson.getUsername());

            if (isEmailTaken || isUsernameTaken) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);


                String message = isEmailTaken ? "Email уже занят" : "Никнейм уже занят";
                String jsonResponse = new Gson().toJson(Map.of(
                        "success", false,
                        "message", message
                ));
                response.getWriter().write(jsonResponse);
                return;
            }

            signUpService.signUp(signUpFromJson);
            response.getWriter().write(new Gson().toJson(Map.of(
                    "success", true,
                    "message", "Регистрация успешна!"
            )));
        } catch (SQLException e) {
            e.printStackTrace();
            response.setContentType("application/json");

            String errorMessage = "Ошибка сервера: " + e.getMessage();
            response.getWriter().write(new Gson().toJson(Map.of(
                    "success", false,
                    "message", errorMessage
            )));
        }
    }

}
