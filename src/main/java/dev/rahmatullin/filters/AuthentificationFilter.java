package dev.rahmatullin.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@WebFilter("/*")
public class AuthentificationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        String requestURI = request.getRequestURI();
        if (requestURI.endsWith(".css") || requestURI.endsWith(".js") || requestURI.endsWith(".jpg") || requestURI.endsWith(".png")) {
            filterChain.doFilter(request, response);
        } else {
            Boolean isAutheticated = false;
            Boolean sessionExists = session != null;
            Boolean isLoginPage = request.getRequestURI().equals("/signIn");
            Boolean isSignUpPage = request.getRequestURI().equals("/signUp");
            if (sessionExists) {
                isAutheticated = (Boolean) session.getAttribute("authenticated");
                if (isAutheticated == null) {
                    isAutheticated = false;
                }
            }

            if ((isAutheticated && !isLoginPage) || (!isAutheticated && isLoginPage) || (!isAutheticated && isSignUpPage)) {
                filterChain.doFilter(request, response);
            } else if (isAutheticated && isLoginPage) {
                response.sendRedirect("/feed");
            } else {
                response.sendRedirect("/signIn");
            }
        }
    }
}
