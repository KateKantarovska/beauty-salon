package com.project.beautysalon.command.admin;

import com.project.beautysalon.command.Command;
import dao.CategoryDao;
import model.Category;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ViewCategoriesCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        CategoryDao categoryDao = new CategoryDao();
        List<Category> categoriesList = categoryDao.listAll();
        request.setAttribute("categoriesList", categoriesList);
        try {
            request.getRequestDispatcher("/jsp/admin/categories-list.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}
