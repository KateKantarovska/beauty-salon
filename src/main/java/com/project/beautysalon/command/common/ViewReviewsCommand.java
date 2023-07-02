package com.project.beautysalon.command.common;

import com.project.beautysalon.command.Command;
import dao.MasterDao;
import dao.ReviewDao;
import model.Client;
import model.Master;
import model.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ViewReviewsCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private HttpServletRequest request;
    private HttpSession session;
    private ReviewDao reviewDao = new ReviewDao();
    private MasterDao masterDao = new MasterDao();
    private User user;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        session = request.getSession();
        user = ((User) session.getAttribute("user"));
        String userRole = (String) session.getAttribute("role");
        String address = switch (userRole) {
            case "client" -> viewReviewsClient();
            case "master" -> viewReviewsMaster();
            case "admin" -> viewReviewsAdmin();
            default -> null;
        };
        try {
            request.getRequestDispatcher(address).forward(request, response);
        } catch (ServletException | IOException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }


    private String viewReviewsClient() {
        var client = (Client) user;
        client.setReviewsList(reviewDao.getByClientId(client.getId())
                .stream()
                .filter(review -> review.isVisible().equals(true))
                .map(review -> {
                    review.setMaster(masterDao.get(review.getMasterID()));
                    return review;
                })
                .toList());
        session.setAttribute("user", client);
        return "/jsp/client/reviews-client.jsp";
    }

    private String viewReviewsMaster() {
        var master = (Master) user;
        master.setReviewsList(reviewDao.getByMasterId(master.getId())
                .stream()
                .filter(review -> review.isVisible().equals(true))
                .toList());
        session.setAttribute("user", master);
        return "/jsp/master/master-reviews.jsp";
    }

    private String viewReviewsAdmin() {
        int masterId = Integer.parseInt(request.getParameter("id"));
        var master = masterDao.get(masterId);
        master.setReviewsList(reviewDao.getByMasterId(masterId));
        request.setAttribute("master", master);
        return "/jsp/admin/master-reviews-admin.jsp";
    }

}
