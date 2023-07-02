package com.project.beautysalon.command.admin;

import com.project.beautysalon.command.Command;
import dao.CategoryDao;
import model.Category;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddNewCategoryCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        var session = request.getSession();
        CategoryDao categoryDao = new CategoryDao();
        String nameUA = request.getParameter("nameUA");
        String nameEN = request.getParameter("nameEN");
        Category category = categoryDao.get(nameUA);
        if (category != null && category.getNameEN().equals(nameEN)) {
            session.setAttribute("message", "message.category-already-exists");
            return "/admin/add-new-category";
        } else {
            category = new Category();
            category.setNameUA(nameUA);
            category.setNameEN(nameEN);
            categoryDao.create(category);
            session.setAttribute("message", "message.category-created");
            return "/app?command=viewCategoriesList";
        }

    }
}
