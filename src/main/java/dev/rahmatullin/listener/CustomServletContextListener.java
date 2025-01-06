package dev.rahmatullin.listener;

import dev.rahmatullin.repositories.CommentRepository;
import dev.rahmatullin.repositories.impl.CommentRepositoryJdbcImpl;
import dev.rahmatullin.repositories.impl.UserRepositoryJdbcImpl;
import dev.rahmatullin.services.SignInService;
import dev.rahmatullin.services.SignUpService;
import dev.rahmatullin.services.UserService;
import dev.rahmatullin.services.impl.CommentServiceImpl;
import dev.rahmatullin.services.impl.SignInServiceImpl;
import dev.rahmatullin.services.impl.SignUpServiceImpl;
import dev.rahmatullin.services.impl.UserServiceImpl;
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

        servletContext.setAttribute("userService", userServiceImpl);
        servletContext.setAttribute("commentService", commentServiceImpl);
        servletContext.setAttribute("signUpService", signUpServiceImpl);
        servletContext.setAttribute("signInService", signInServiceImpl);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
