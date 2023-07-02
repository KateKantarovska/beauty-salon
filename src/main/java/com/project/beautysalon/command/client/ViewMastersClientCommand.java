package com.project.beautysalon.command.client;

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

public class ViewMastersClientCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        var masterDao = new MasterDao();
        List<Master> mastersList = masterDao.listAll();
        String sortBy = request.getParameter("sort");
        if (sortBy != null) {
            switch (sortBy) {
                case "name" -> mastersList.sort(Comparator.comparing(Master::getNameEN).thenComparing(Master::getSurnameEN));
                case "rating" -> mastersList.sort(Comparator.comparing(Master::getRating).reversed());
            }
        }
        request.setAttribute("mastersList", mastersList);
        try {
            request.getRequestDispatcher("/jsp/client/masters-list.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}
