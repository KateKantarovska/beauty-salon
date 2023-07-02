package com.project.beautysalon.command.admin;

import com.project.beautysalon.command.Command;
import dao.ServiceDao;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditServiceFormCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        var serviceDao = new ServiceDao();
        int serviceId = Integer.parseInt(request.getParameter("id"));
        var service = serviceDao.get(serviceId);
        request.setAttribute("service", service);
        try {
            request.getRequestDispatcher("/jsp/admin/edit-service.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}
