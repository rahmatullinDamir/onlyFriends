//package dev.rahmatullin.servlets;
//
//import dev.rahmatullin.models.User;
//import dev.rahmatullin.services.UserService;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.List;
//
//public class UserServlet extends HttpServlet {
//    private final UserService userService = new UserService(); // userRepository
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String id = req.getParameter("id");
//        if (id == null) {
//            List<User> users = userService.findAll();
//            req.setAttribute("users", users);
//            req.getRequestDispatcher("/users.jsp").forward(req, resp);
//        } else {
//            User user = userService.findById(Long.parseLong(id));
//            req.setAttribute("user", user);
//            req.getRequestDispatcher("/user.jsp").forward(req, resp);
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String id = req.getParameter("id");
//        String name = req.getParameter("name");
//        String age = req.getParameter("age");
//        if (id == null) {
//            userService.save(User.builder()
//                    .name(name)
//                    .age(Integer.parseInt(age))
//                    .build());
//        } else {
//            userService.update(User.builder()
//                    .id(Long.parseLong(id))
//                    .name(name)
//                    .age(Integer.parseInt(age))
//                    .build());
//        }
//        resp.sendRedirect("/users");
//    }
//
////    @Override
////    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
////        String id = req.getParameter("id");
////        userService.delete(Long.parseLong(id));
////    }
//}
