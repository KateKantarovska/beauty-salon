package dao;

import model.Category;
import model.Service;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao implements GenericDao<Category> {
    private static final Logger logger = LogManager.getLogger();
    private final DataSource dataSource;
    ModelBuilder modelBuilder;

    public CategoryDao() {
        dataSource = DatabaseUtil.getDataSource();
    }

    public CategoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void create(Category category) {
        String insertCategory = "INSERT INTO services_categories VALUES(default, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertCategory, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, category.getNameUA());
            statement.setString(2, category.getNameEN());
            statement.executeUpdate();
            ResultSet generatedKey = statement.getGeneratedKeys();
            if (generatedKey.next()) {
                category.setId(generatedKey.getInt(1));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
    }

    @Override
    public void update(Category category) {
        String updateCategory = "UPDATE services_categories SET name_ua=?, name_en=? WHERE id=?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(updateCategory)) {
            statement.setString(1, category.getNameUA());
            statement.setString(2, category.getNameEN());
            statement.setInt(3, category.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
    }

    @Override
    public Category get(Integer id) {
        String findCategory = "SELECT * FROM services_categories WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(findCategory)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            modelBuilder = new ModelBuilder(resultSet);
            if (resultSet.next()) {
                return modelBuilder.buildCategoryObject(true);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }

    public Category get(String nameUA) {
        String findCategory = "SELECT * FROM services_categories WHERE name_ua=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(findCategory)) {
            statement.setString(1, nameUA);
            ResultSet resultSet = statement.executeQuery();
            modelBuilder = new ModelBuilder(resultSet);
            if (resultSet.next()) {
                return modelBuilder.buildCategoryObject(true);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        String deleteCategory = "DELETE FROM services_categories WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteCategory)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
    }

    @Override
    public List<Category> listAll() {
        String selectAllCategories = "SELECT * FROM services_categories";
        List<Category> categoriesList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectAllCategories)) {
            ResultSet resultSet = statement.executeQuery();
            modelBuilder = new ModelBuilder(resultSet);
            while (resultSet.next()) {
                categoriesList.add(modelBuilder.buildCategoryObject(true));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
        return categoriesList;
    }

    List<Service> getServicesOfCategory(int categoryId) {
        String selectServices = "SELECT * FROM services WHERE category_id=?";
        List<Service> servicesList= new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectServices)) {
            statement.setInt(1, categoryId);
            ResultSet resultSet = statement.executeQuery();
            modelBuilder = new ModelBuilder(resultSet);
            while (resultSet.next()) {
                servicesList.add(modelBuilder.buildServiceObject(false));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
        return servicesList;
    }
}
