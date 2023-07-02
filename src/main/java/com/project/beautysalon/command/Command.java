package com.project.beautysalon.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {

    String execute(HttpServletRequest request, HttpServletResponse response);

}
