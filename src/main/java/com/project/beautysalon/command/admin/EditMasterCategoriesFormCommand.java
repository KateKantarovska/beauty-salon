package com.project.beautysalon.command.admin;

import com.project.beautysalon.command.Command;
import dao.CategoryDao;
import dao.MasterDao;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditMasterCategoriesFormCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int masterId = Integer.parseInt(request.getParameter("id"));
        var masterDao = new MasterDao();
        var master = masterDao.get(masterId);
        request.setAttribute("master", master);
        var categoryDao = new CategoryDao();
        var categoriesList = categoryDao.listAll();
        request.setAttribute("categories", categoriesList);
        try {
            request.getRequestDispatcher("/jsp/admin/edit-masters-services.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}
