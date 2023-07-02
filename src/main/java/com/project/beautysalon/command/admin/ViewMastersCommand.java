package com.project.beautysalon.command.admin;

import com.project.beautysalon.command.Command;
import dao.MasterDao;
import model.Master;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class ViewMastersCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        MasterDao masterDao = new MasterDao();
        List<Master> mastersList = masterDao.listAll();
        mastersList.sort(Comparator.comparing(Master::getName).thenComparing(Master::getSurname));
        request.setAttribute("mastersList", mastersList);
        try {
            request.getRequestDispatcher("/jsp/admin/masters-list-admin.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}
