package com.project.beautysalon.command.admin;

import com.project.beautysalon.command.Command;
import dao.MasterDao;
import dao.ReviewDao;
import model.Review;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeReviewVisibilityCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int reviewId = Integer.parseInt(request.getParameter("id"));
        var reviewDao = new ReviewDao();
        var review = reviewDao.get(reviewId);
        review.setVisible(!review.isVisible());
        reviewDao.update(review);

        var masterDao = new MasterDao();
        var master = masterDao.get(review.getMasterID());
        master.setReviewsList(reviewDao.getByMasterId(master.getId()));

        double newMasterRating = master.getReviewsList()
                    .stream()
                    .filter(Review::isVisible)
                    .map(Review::getRating)
                    .mapToDouble(Integer::doubleValue)
                    .average()
                    .orElse(0.0);
        master.setRating(newMasterRating);
        masterDao.update(master);

        return "/app?command=viewReviews&id=" + master.getId();
    }
}
