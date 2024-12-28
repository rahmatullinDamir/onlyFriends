package dev.rahmatullin.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter("/*")
public class RolesFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        if (session.getAttribute("isAdmin") != null) {
            Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
            if (isAdmin && request.getRequestURI().equals("/admin")) {
                filterChain.doFilter(request, response);
            }
            else {
                if (!request.getRequestURI().equals("/admin")) {
                    filterChain.doFilter(request, response);
                }
                else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                }
            }
        }
        else {
            filterChain.doFilter(request, response);
        }
    }
}
