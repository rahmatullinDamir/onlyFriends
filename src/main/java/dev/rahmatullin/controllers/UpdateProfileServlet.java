package dev.rahmatullin.controllers;


import dev.rahmatullin.dto.UserDto;
import dev.rahmatullin.models.Image;
import dev.rahmatullin.models.User;
import dev.rahmatullin.services.impl.ImageServiceImpl;
import dev.rahmatullin.services.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

@WebServlet("/updateProfile")

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 50 // 50 MB
)
public class UpdateProfileServlet extends HttpServlet {
    private static final String UPLOAD_DIR = "static/images/avatars";
    private ImageServiceImpl imageService;
    private UserServiceImpl userService;
    private Image imageFromDb;
    private User userToUpdate;

    @Override
    public void init() throws ServletException {
        imageService = (ImageServiceImpl) getServletConfig().getServletContext().getAttribute("imageService");
        userService = (UserServiceImpl) getServletConfig().getServletContext().getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        Long id = (Long) httpSession.getAttribute("id");


        String name = getStringFromPart(request.getPart("name"));
        String surname = getStringFromPart(request.getPart("surname"));
        Integer age = Integer.parseInt(getStringFromPart(request.getPart("age")));
        Part filePart = request.getPart("avatar");


        if (filePart != null && filePart.getSize() > 0) {

            String uploadPath = "/Users/damirrakhmatullin/Desktop/univesity/ОРИС/practice/onlyFriendsSite/src/main/webapp/static/images/avatars";

            String fileName = filePart.getSubmittedFileName();
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);

            String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
            String filePath = uploadPath + File.separator + uniqueFileName;

            filePart.write(filePath);

            Image image = Image.builder()
                    .name(uniqueFileName)
                    .url(UPLOAD_DIR + "/" + uniqueFileName)
                    .extension(fileExtension)
                    .build();

            try {
                imageService.saveImageToDb(image);
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error saving data to the database.");
                return;
            }
            try {
                imageFromDb = imageService.getImageFromDbByName(uniqueFileName);
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error to get data.");
                return;
            }
            userToUpdate = User.builder()
                    .id(id)
                    .surname(surname)
                    .name(name)
                    .age(age)
                    .avatarImageId(imageFromDb.getId())
                    .build();
            userService.updateUserInDb(userToUpdate);

        } else {
            userToUpdate = User.builder()
                    .id(id)
                    .surname(surname)
                    .name(name)
                    .age(age)
                    .build();
            userService.updateUserInDbWithoutImage(userToUpdate);
        }


        response.sendRedirect(request.getContextPath() + "/logout");
    }

    public String getStringFromPart(Part part) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream = part.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        }
        return stringBuilder.toString().trim();
    }
}