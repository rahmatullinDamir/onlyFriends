package dev.rahmatullin.controllers;

import dev.rahmatullin.dto.UserDto;
import dev.rahmatullin.services.UserService;
import dev.rahmatullin.services.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin")
public class AdminPageServlet extends HttpServlet {
    private UserService userService;
    @Override
    public void init() throws ServletException {
        userService = (UserServiceImpl) getServletConfig().getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<UserDto> users = userService.getAllUsers();
            request.setAttribute("users", users);
            request.getRequestDispatcher("/jsp/admin.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Ошибка при загрузке списка пользователей", e);
        }
    }

}

