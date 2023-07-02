package com.project.beautysalon.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AdminFilter", urlPatterns = "/admin/*")
public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var request = ((HttpServletRequest) servletRequest);
        var response = ((HttpServletResponse) servletResponse);
        String role = (String) request.getSession().getAttribute("role");
        if (role == null || !role.equals("admin")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
