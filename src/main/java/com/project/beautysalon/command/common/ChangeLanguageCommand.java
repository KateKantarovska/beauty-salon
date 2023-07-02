package com.project.beautysalon.command.common;

import com.project.beautysalon.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeLanguageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String language = request.getParameter("lang");
        request.getSession().setAttribute("lang", language);
        return "/home";
    }
}
