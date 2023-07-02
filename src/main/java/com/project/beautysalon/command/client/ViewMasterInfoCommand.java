package com.project.beautysalon.command.client;

import com.project.beautysalon.command.Command;
import dao.MasterDao;
import dao.ReviewDao;
import model.Review;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;

public class ViewMasterInfoCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int masterId = Integer.parseInt(request.getParameter("id"));
        var masterDao = new MasterDao();
        var master = masterDao.get(masterId);
        var reviewDao = new ReviewDao();
        master.setReviewsList(reviewDao.getByMasterId(masterId)
                .stream()
                .filter(review -> review.isVisible().equals(true))
                .sorted(Comparator.comparing(Review::getRating).reversed())
                .toList());

        request.setAttribute("master", master);
        try {
            request.getRequestDispatcher("/jsp/client/master-info-client.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}
