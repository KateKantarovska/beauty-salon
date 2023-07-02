package com.project.beautysalon.command.client;

import com.project.beautysalon.command.Command;
import dao.MasterDao;
import dao.ReviewDao;
import model.Client;
import model.Review;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddReviewCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int clientId = ((Client) request.getSession().getAttribute("user")).getId();
        int masterId = Integer.parseInt(request.getParameter("masterId"));
        int rating = Integer.parseInt(request.getParameter("rating"));
        String feedback = request.getParameter("feedback");

        var reviewDao = new ReviewDao();
        var review = new Review();
        review.setClientID(clientId);
        review.setMasterID(masterId);
        review.setRating(rating);
        review.setText(feedback);
        reviewDao.create(review);

        var masterReviewsList = reviewDao.getByMasterId(masterId);
        double newMasterRating = masterReviewsList
                .stream()
                .filter(r -> r.isVisible().equals(true))
                .map(Review::getRating)
                .mapToDouble(Integer::doubleValue)
                .average()
                .orElse(0.0);

        var masterDao = new MasterDao();
        var master = masterDao.get(masterId);
        master.setRating(newMasterRating);
        masterDao.update(master);
        request.getSession().setAttribute("message", "message.thanks-for-review");

        return "/app?command=viewMasterInfo&id=" + masterId;
    }
}
