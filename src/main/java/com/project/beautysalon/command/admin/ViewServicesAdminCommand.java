package com.project.beautysalon.command.admin;

import com.project.beautysalon.command.Command;
import dao.CategoryDao;
import dao.ServiceDao;
import model.Service;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class ViewServicesAdminCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        var categoryDao = new CategoryDao();
        var serviceDao = new ServiceDao();
        var categoriesList = categoryDao.listAll();
        var servicesList = serviceDao.listAll();
        Map<Integer, List<Service>> servicesMap = new TreeMap<>();
        servicesList.forEach(service -> {
            int id = service.getCategoryId();
            servicesMap.putIfAbsent(id, new ArrayList<>());
            servicesMap.get(id).add(service);
        });
        servicesMap.forEach((id, list) -> list.sort(Comparator.comparing(Service::getNameUA)));
        request.setAttribute("categoriesList", categoriesList);
        request.setAttribute("servicesMap", servicesMap);
        try {
            request.getRequestDispatcher("/jsp/admin/services-list-admin.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}
