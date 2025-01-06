package dev.rahmatullin.controllers;

import dev.rahmatullin.dto.UserDto;
import dev.rahmatullin.services.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/profile/*")
public class ProfilePageServlet extends HttpServlet {
    private UserServiceImpl userServiceImpl;
    @Override
    public void init() throws ServletException {
        userServiceImpl = (UserServiceImpl) getServletConfig().getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        String pathInfo = request.getPathInfo();
        String username = pathInfo.substring(1);
        try {
            UserDto user = userServiceImpl.getUserByUsername(username);
            if (user != null) {
                String curUsername = (String) request.getSession().getAttribute("username");
                Boolean isOwner = username.equals(curUsername);

                request.setAttribute("user", user);
                request.setAttribute("isOwner", isOwner);
                request.setAttribute("name", user.getName() + " " + user.getSurname());
                request.setAttribute("age", user.getAge());

                request.getRequestDispatcher("/jsp/profile.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
