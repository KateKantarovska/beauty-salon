package com.project.beautysalon.command.admin;

import com.project.beautysalon.command.Command;
import dao.ServiceDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteServiceCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        ServiceDao serviceDao = new ServiceDao();
        int id = Integer.parseInt(request.getParameter("id"));
        serviceDao.delete(id);
        request.setAttribute("message", "message.service-deleted");
        return "/app?command=viewServicesList";
    }
}
