package com.project.beautysalon.command.admin;

import com.project.beautysalon.command.Command;
import dao.CategoryDao;
import model.Category;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditCategoryCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        var session = request.getSession();
        var categoryDao = new CategoryDao();
        int id = Integer.parseInt(request.getParameter("id"));
        String nameUA = request.getParameter("nameUA");
        String nameEN = request.getParameter("nameEN");
        var category = categoryDao.get(nameUA);
        if (category != null && category.getId() != id) {
            session.setAttribute("message", "message.category-already-exists");
            request.setAttribute("category", category);
            return "/jsp/admin/edit-category.jsp";
        } else {
            category = new Category();
            category.setId(id);
            category.setNameUA(nameUA);
            category.setNameEN(nameEN);
            categoryDao.update(category);
            session.setAttribute("message", "message.category-updated");
            return "/app?command=viewCategoriesList";
        }
    }
}
