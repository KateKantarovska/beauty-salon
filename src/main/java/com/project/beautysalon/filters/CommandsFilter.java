package com.project.beautysalon.filters;

import com.project.beautysalon.command.CommandAccess;
import com.project.beautysalon.command.Commands;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "CommandsFilter", urlPatterns = "/app")
public class CommandsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var request = ((HttpServletRequest) servletRequest);
        var response = ((HttpServletResponse) servletResponse);
        String commandName = request.getParameter("command");
        String role = (String) request.getSession().getAttribute("role");
        var accessLevel = Commands.COMMAND_ACCESS_MAP.get(commandName);
        if (accessLevel == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } else if (role == null) {
            if (!accessLevel.contains(CommandAccess.GUEST)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            var accessLevelString = accessLevel.stream().map(Enum::toString).toList();
            if (!accessLevelString.contains(role.toUpperCase())) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

}
