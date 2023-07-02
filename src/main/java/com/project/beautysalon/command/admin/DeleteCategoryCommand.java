package com.project.beautysalon.command.admin;

import com.project.beautysalon.command.Command;
import dao.CategoryDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteCategoryCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        CategoryDao categoryDao = new CategoryDao();
        Integer id = Integer.parseInt(request.getParameter("id"));
        categoryDao.delete(id);
        request.getSession().setAttribute("message", "message.category-deleted");
        return "/app?command=viewCategoriesList";
    }
}
