package dev.rahmatullin.controllers;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.rahmatullin.models.Friends;
import dev.rahmatullin.services.UserService;
import dev.rahmatullin.services.impl.FriendServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/removeFriend")
public class RemoveFriendServlet extends HttpServlet {
    FriendServiceImpl friendService;
    @Override
    public void init() throws ServletException {
        friendService = (FriendServiceImpl) getServletConfig().getServletContext().getAttribute("friendService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder jsonBuffer = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
        }
        JsonObject jsonObject = JsonParser.parseString(jsonBuffer.toString()).getAsJsonObject();
        Long userId = jsonObject.get("userId").getAsLong();
        Long friendId = jsonObject.get("friendId").getAsLong();
        boolean success = false;
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        JsonObject jsonResponse = new JsonObject();

        if (friendId > 0) {
            try {
                Friends friends = friendService.getFriend(userId, friendId);
                friendService.removeFriend(friends);
                success = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        jsonResponse.addProperty("success", success);
        if (success) {
            jsonResponse.addProperty("message", "Пользователь успешно удален из друзей!");
        } else {
            jsonResponse.addProperty("message", "Не удалось удалить друга.");
        }
        response.getWriter().write(jsonResponse.toString());
    }


}
