package dev.rahmatullin.controllers;

import dev.rahmatullin.dto.PostDto;
import dev.rahmatullin.models.Image;
import dev.rahmatullin.models.Post;
import dev.rahmatullin.services.ImageService;
import dev.rahmatullin.services.impl.ImageServiceImpl;
import dev.rahmatullin.services.impl.PostServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet("/addPost")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 5 * 1024 * 1024,  // 5MB
        maxRequestSize = 10 * 1024 * 1024 // 10MB
)
public class AddPostPageServlet extends HttpServlet {
    private static final String UPLOAD_DIR = "static/images/posts";
    private PostServiceImpl postService;
    private ImageService imageService;

    @Override
    public void init() throws ServletException {
        postService = (PostServiceImpl) getServletConfig().getServletContext().getAttribute("postService");
        imageService = (ImageServiceImpl) getServletConfig().getServletContext().getAttribute("imageService");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/add-post.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = getStringFromPart(request.getPart("title"));
        String content = getStringFromPart(request.getPart("content"));

        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("id");

        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Post post = Post.builder()
                .userId(userId)
                .title(title)
                .text(content)
                .build();

        try {
            postService.addPost(post);
            response.sendRedirect(request.getContextPath() + "/profile/" + session.getAttribute("username"));
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка добавления поста");
            throw new RuntimeException(e);
        }

        try {
            List<Part> fileParts = request.getParts().stream()
                    .filter(part -> "image".equals(part.getName()) && part.getSize() > 0)
                    .toList();

            for (Part filePart : fileParts) {
                String uploadPath = "/Users/damirrakhmatullin/Desktop/univesity/ОРИС/practice/onlyFriendsSite/src/main/webapp/static/images/posts";
                String originalFileName = filePart.getSubmittedFileName();
                String uniqueFileName = UUID.randomUUID().toString() + "." + getFileExtension(originalFileName);
                String filePath = uploadPath + File.separator + uniqueFileName;

                filePart.write(filePath);

                Image image = Image.builder()
                        .name(uniqueFileName)
                        .url(UPLOAD_DIR + "/" + uniqueFileName)
                        .extension(uniqueFileName.substring(uniqueFileName.lastIndexOf(".") + 1))
                        .build();

                imageService.saveImageToDb(image);
                List<PostDto> userPosts = postService.getPostsWithUserId(userId);
                Long lastPostId = userPosts.get(userPosts.size() - 1).getId();
                Long imageId = imageService.getImageFromDbByName(uniqueFileName).getId();
                postService.addImageToPost(lastPostId, imageId);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
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
    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex > 0) ? fileName.substring(dotIndex + 1) : "";
    }

}
