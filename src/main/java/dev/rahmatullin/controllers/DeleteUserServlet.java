package dev.rahmatullin.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.rahmatullin.services.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/delete-user")
public class DeleteUserServlet extends HttpServlet {
    private UserServiceImpl userService;

    @Override
    public void init() throws ServletException {
        userService = (UserServiceImpl) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();

        JsonObject jsonBody = gson.fromJson(request.getReader(), JsonObject.class);

        Long userId = jsonBody.get("userId").getAsLong();

        try {
            userService.deleteUserById(userId);
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": true}");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка при блокировке пользователя");
        }
    }
}
