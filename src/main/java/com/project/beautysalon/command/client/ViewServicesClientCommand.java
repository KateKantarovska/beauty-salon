package com.project.beautysalon.command.client;

import com.project.beautysalon.command.Command;
import dao.CategoryDao;
import dao.MasterDao;
import dao.ServiceDao;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ViewServicesClientCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        var serviceDao = new ServiceDao();
        var categoryDao = new CategoryDao();
        var masterDao = new MasterDao();
        var servicesList = serviceDao.listAll();
        var categoriesList = categoryDao.listAll();
        var mastersList = masterDao.listAll();
        String filterBy = request.getParameter("filter");
        if (filterBy != null) {
            switch (filterBy) {
                case "master" -> {
                    int masterId = Integer.parseInt(request.getParameter("master"));
                    servicesList = servicesList
                            .stream()
                            .filter(service -> service.getMastersList()
                                    .stream()
                                    .anyMatch(master -> master.getId().equals(masterId)))
                            .toList();
                }
                case "category" -> {
                    int categoryId = Integer.parseInt(request.getParameter("category"));
                    servicesList = servicesList
                            .stream()
                            .filter(service -> service.getCategoryId().equals(categoryId))
                            .toList();
                }
            }
        }
        String pageNumber = request.getParameter("page");
        int page;
        if (pageNumber == null) {
            page = 1;
        } else {
            page = Integer.parseInt(pageNumber);
        }
        request.setAttribute("page", page);
        request.setAttribute("servicesList", servicesList);
        request.setAttribute("categoriesList", categoriesList);
        request.setAttribute("mastersList", mastersList);
        try {
            request.getRequestDispatcher("/jsp/client/services-list.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}
