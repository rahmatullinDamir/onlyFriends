package dev.rahmatullin.controllers;

import dev.rahmatullin.dto.FriendDto;
import dev.rahmatullin.dto.FriendsDto;
import dev.rahmatullin.dto.UserDto;
import dev.rahmatullin.services.impl.FriendServiceImpl;
import dev.rahmatullin.services.impl.UserServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/friends")
public class FriendsPageServlet extends HttpServlet {
    FriendServiceImpl friendService;
    UserServiceImpl userService;

    @Override
    public void init() throws ServletException {
        friendService = (FriendServiceImpl) getServletConfig().getServletContext().getAttribute("friendService");
        userService = (UserServiceImpl) getServletConfig().getServletContext().getAttribute("userService");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        Long userId = (Long)httpSession.getAttribute("id");
        if (userId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            try {
                List<FriendDto> friends = friendService.getFriends(Long.valueOf(userId));
                List<FriendDto> friendRequests = friendService.getPendingRequest(Long.valueOf(userId));
                Long curId = (Long) request.getSession().getAttribute("id");
                Boolean isOwner = curId.equals(Long.valueOf(userId));
                request.setAttribute("friends", friends);
                request.setAttribute("friendRequests", friendRequests);
                request.setAttribute("isOwner", isOwner);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            request.getRequestDispatcher("/jsp/friends.jsp").forward(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String action = request.getParameter("action");
        String requestId = request.getParameter("requestId");


        if (username == null && action == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        Long curId = (Long) request.getSession().getAttribute("id");

        if (action.equals("accept")) {
            try {
                FriendsDto friendsDto = FriendsDto.builder()
                        .userId(curId)
                        .friendId(Long.valueOf(requestId))
                        .build();
                friendService.acceptFriendRequest(friendsDto);

                response.sendRedirect(request.getContextPath() + "/friends?id=" + curId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (action.equals("reject")) {
            try {
                FriendsDto friendshipDto = FriendsDto.builder()
                        .userId(curId)
                        .friendId(Long.valueOf(requestId))
                        .build();
                friendService.rejectFriendRequest(friendshipDto);
                response.sendRedirect(request.getContextPath() + "/friends?id=" + curId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
