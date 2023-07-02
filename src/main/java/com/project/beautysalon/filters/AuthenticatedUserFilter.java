package com.project.beautysalon.filters;

import model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AuthenticatedUserFilter", urlPatterns = {"/registration", "/sign-in"})
public class AuthenticatedUserFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var request = ((HttpServletRequest) servletRequest);
        var user = ((User) request.getSession().getAttribute("user"));
        if (user != null) {
            var response = ((HttpServletResponse) servletResponse);
            response.sendRedirect(request.getContextPath() + "/home");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
