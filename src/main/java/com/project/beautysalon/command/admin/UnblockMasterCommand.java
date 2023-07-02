package com.project.beautysalon.command.admin;

import com.project.beautysalon.command.Command;
import dao.MasterDao;
import model.Master;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UnblockMasterCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        MasterDao masterDao = new MasterDao();
        int id = Integer.parseInt(request.getParameter("id"));
        Master master = masterDao.get(id);
        master.setActive(true);
        masterDao.update(master);
        return "/app?command=viewMastersList";
    }
}
