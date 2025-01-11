package dev.rahmatullin.controllers;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
@WebServlet("/static/images/*")
public class ImageUpdateServlet extends HttpServlet {
    private static final String POSTS_DIR = "/Users/damirrakhmatullin/Desktop/univesity/ОРИС/practice/onlyFriendsSite/src/main/webapp/static/images/posts";
    private static final String AVATARS_DIR = "/Users/damirrakhmatullin/Desktop/univesity/ОРИС/practice/onlyFriendsSite/src/main/webapp/static/images/avatars";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getPathInfo();
        if (path == null || path.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Запрошен неправильный путь.");
            return;
        }

        String[] parts = path.split("/", 3);
        if (parts.length < 2) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Некорректный путь.");
            return;
        }

        String type = parts[1];
        String filename = parts[2];
        String directory;
        if ("avatars".equals(type)) {
            directory = AVATARS_DIR;
        } else if ("posts".equals(type)) {
            directory = POSTS_DIR;
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неизвестный тип ресурсов.");
            return;
        }

        File file = new File(directory, filename);

        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType(getServletContext().getMimeType(file.getName()));
        response.setContentLength((int) file.length());

        try (FileInputStream in = new FileInputStream(file); OutputStream out = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }
}
