package com.project.beautysalon.command.admin;

import com.project.beautysalon.command.Command;
import dao.MasterDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditMasterCategoriesCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int masterId = Integer.parseInt(request.getParameter("masterId"));
        MasterDao masterDao = new MasterDao();
        int[] categoriesIds = request.getParameterMap().keySet().stream()
                .filter(key -> key.matches("\\d+")).mapToInt(Integer::valueOf).toArray();
        masterDao.updateMastersCategories(masterId, categoriesIds);
        return "/app?command=viewMastersList";
    }
}
