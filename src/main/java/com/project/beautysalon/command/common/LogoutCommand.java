package com.project.beautysalon.command.common;

import com.project.beautysalon.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        var session = request.getSession();
        session.removeAttribute("user");
        session.removeAttribute("role");
        return "/home";
    }
}
