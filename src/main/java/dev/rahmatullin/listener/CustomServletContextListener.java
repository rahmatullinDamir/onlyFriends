package dev.rahmatullin.listener;

import dev.rahmatullin.repositories.CommentRepository;
import dev.rahmatullin.repositories.impl.*;
import dev.rahmatullin.services.*;
import dev.rahmatullin.services.impl.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class CustomServletContextListener implements ServletContextListener {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "admin";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/onlyFriendsDb";
    private static final String DB_DRIVER = "org.postgresql.Driver";
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setUrl(DB_URL);

        UserRepositoryJdbcImpl userRepositoryJdbc = new UserRepositoryJdbcImpl(dataSource);
        SignInService signInServiceImpl = new SignInServiceImpl(userRepositoryJdbc);
        SignUpService signUpServiceImpl = new SignUpServiceImpl(userRepositoryJdbc);

        CommentRepository commentRepositoryJdbcImpl = new CommentRepositoryJdbcImpl(dataSource);
        CommentServiceImpl commentServiceImpl = new CommentServiceImpl(commentRepositoryJdbcImpl);

        UserService userServiceImpl = new UserServiceImpl(userRepositoryJdbc);

        ImageRepositoryJdbcImpl imageRepositoryJdbc = new ImageRepositoryJdbcImpl(dataSource);
        ImageServiceImpl imageServiceImpl = new ImageServiceImpl(imageRepositoryJdbc);

        PostRepositoryJdbcImpl postRepositoryJdbc = new PostRepositoryJdbcImpl(dataSource);
        PostServiceImpl postServiceImpl = new PostServiceImpl(postRepositoryJdbc);

        FriendsRepositoryJdbcImpl friendsRepositoryJdbc = new FriendsRepositoryJdbcImpl(dataSource);
        FriendServiceImpl friendServiceImpl = new FriendServiceImpl(friendsRepositoryJdbc, userRepositoryJdbc);

        LikesRepositoryJdbcImpl likesRepositoryJdbc = new LikesRepositoryJdbcImpl(dataSource);
        LikesServiceImpl likesServiceImpl = new LikesServiceImpl(likesRepositoryJdbc);


        servletContext.setAttribute("likesService", likesServiceImpl);
        servletContext.setAttribute("friendService", friendServiceImpl);
        servletContext.setAttribute("postService", postServiceImpl);
        servletContext.setAttribute("imageService", imageServiceImpl);
        servletContext.setAttribute("userService", userServiceImpl);
        servletContext.setAttribute("commentService", commentServiceImpl);
        servletContext.setAttribute("signUpService", signUpServiceImpl);
        servletContext.setAttribute("signInService", signInServiceImpl);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
