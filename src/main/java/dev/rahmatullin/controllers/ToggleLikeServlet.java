package dev.rahmatullin.controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.rahmatullin.services.impl.LikesServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/toggle-like")
public class ToggleLikeServlet extends HttpServlet {
    private LikesServiceImpl likesService;

    @Override
    public void init() throws ServletException {
        likesService = (LikesServiceImpl) getServletConfig().getServletContext().getAttribute("likesService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        boolean isLiked;
        Long likeCount;

        try {
            BufferedReader reader = req.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JsonObject json = JsonParser.parseString(sb.toString()).getAsJsonObject();
            Long postId = json.get("postId").getAsLong();

            HttpSession session = req.getSession();
            Long userId = (Long) session.getAttribute("id");

            if (userId == null) {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.getWriter().write("{\"error\":\"Необходима авторизация\"}");
                return;
            }

            try {
                Long likeId = likesService.getLikeIdByPostAndUser(postId, userId);
                    if (likeId != null) {
                        likesService.removeLike(likeId, postId, userId);
                        isLiked = false;
                    } else {
                        likesService.addLike(postId, userId);
                        isLiked = true;
                    }
                    likeCount = likesService.getLikeCount(postId);
                } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            JsonObject responseJson = new JsonObject();
            responseJson.addProperty("liked", isLiked);
            responseJson.addProperty("likeCount", likeCount);

            resp.getWriter().write(responseJson.toString());
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}