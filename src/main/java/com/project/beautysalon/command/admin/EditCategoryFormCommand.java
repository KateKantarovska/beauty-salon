package com.project.beautysalon.command.admin;

import com.project.beautysalon.command.Command;
import dao.CategoryDao;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditCategoryFormCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        var categoryDao = new CategoryDao();
        int id = Integer.parseInt(request.getParameter("id"));
        var category = categoryDao.get(id);
        request.setAttribute("category", category);
        try {
            request.getRequestDispatcher("/jsp/admin/edit-category.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}
