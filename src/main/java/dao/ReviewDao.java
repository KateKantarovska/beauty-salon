package dao;

import model.Review;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDao implements GenericDao<Review>{
    private static final Logger logger = LogManager.getLogger();
    private final DataSource dataSource;
    ModelBuilder modelBuilder;

    public ReviewDao() {
        dataSource = DatabaseUtil.getDataSource();
    }

    public ReviewDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void create(Review review) {
        String insertReview = "INSERT INTO reviews VALUES(default, ?, ?, ?, ?, default)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertReview, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, review.getClientID());
            statement.setInt(2, review.getMasterID());
            statement.setString(3, review.getText());
            statement.setInt(4, review.getRating());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
    }

    @Override
    public void update(Review review) {
        String updateReview = "UPDATE reviews SET visible=? WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateReview)){
            statement.setBoolean(1, review.isVisible());
            statement.setInt(2, review.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
    }

    @Override
    public Review get(Integer id) {
        String getReview = "SELECT * FROM reviews WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(getReview)){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            modelBuilder = new ModelBuilder(resultSet);
            if (resultSet.next()) {
                return modelBuilder.buildReviewObject();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }

    public List<Review> getByMasterId(Integer masterId) {
        String getReviews = "SELECT * FROM reviews WHERE master_id=?";
        return getByUserId(masterId, getReviews);
    }

    public List<Review> getByClientId(Integer clientId) {
        String getReviews = "SELECT * FROM reviews WHERE client_id=?";
        return getByUserId(clientId, getReviews);
    }

    private List<Review> getByUserId(Integer userId, String query) {
        List<Review> reviewsList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            modelBuilder = new ModelBuilder(resultSet);
            while (resultSet.next()) {
                reviewsList.add(modelBuilder.buildReviewObject());
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
        return reviewsList;
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Review> listAll() {
        throw new UnsupportedOperationException();
    }
}
