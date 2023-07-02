package com.project.beautysalon.filters;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "ClientFilter", urlPatterns = {"/client/*", "/success"})
public class ClientFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        var request = ((HttpServletRequest) servletRequest);
        var response = ((HttpServletResponse) servletResponse);
        String role = (String) request.getSession().getAttribute("role");
        if (role == null || !role.equals("client")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

}
