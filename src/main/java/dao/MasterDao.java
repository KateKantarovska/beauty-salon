package dao;

import model.Category;
import model.Master;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MasterDao implements UserDao<Master> {
    private static final Logger logger = LogManager.getLogger();
    private final DataSource dataSource;
    ModelBuilder modelBuilder;

    public MasterDao() {
        dataSource = DatabaseUtil.getDataSource();
    }

    public MasterDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void create(Master master) {
        String insertMaster = "INSERT INTO masters VALUES(default, ?, ?, ?, ?, ?, ?, ?, default, default)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertMaster, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, master.getEmail());
            statement.setString(2, master.getPassword());
            statement.setString(3, master.getName());
            statement.setString(4, master.getSurname());
            statement.setString(5, master.getNameEN());
            statement.setString(6, master.getSurnameEN());
            statement.setString(7, master.getPhoneNumber());
            statement.executeUpdate();
            ResultSet generatedKey = statement.getGeneratedKeys();
            if (generatedKey.next()) {
                master.setId(generatedKey.getInt(1));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
    }

    @Override
    public void update(Master master) {
        String updateMaster = "UPDATE masters SET email=?, password=?, name_ua=?, surname_ua=?, name_en=?, surname_en=?, phone_number=?, rating=?, active=? WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateMaster)) {
            statement.setString(1, master.getEmail());
            statement.setString(2, master.getPassword());
            statement.setString(3, master.getName());
            statement.setString(4, master.getSurname());
            statement.setString(5, master.getNameEN());
            statement.setString(6, master.getSurnameEN());
            statement.setString(7, master.getPhoneNumber());
            statement.setDouble(8, master.getRating());
            statement.setBoolean(9, master.isActive());
            statement.setInt(10, master.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
    }

    public void updateMastersCategories(int masterId, int[] categoriesIds) {
        String clearAllRecords = "DELETE FROM masters_services WHERE master_id=?";
        String insertCategories = "INSERT INTO masters_services VALUES(?, ?)";
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statementDelete = connection.prepareStatement(clearAllRecords)) {
                statementDelete.setInt(1, masterId);
                statementDelete.executeUpdate();
            }
            if (categoriesIds != null) {
                try (PreparedStatement statementInsert = connection.prepareStatement(insertCategories)) {
                    for (int categoryId : categoriesIds) {
                        statementInsert.setInt(1, masterId);
                        statementInsert.setInt(2, categoryId);
                        statementInsert.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
    }

    @Override
    public Master get(Integer id) {
        String findMaster = "SELECT * FROM masters WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(findMaster)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            modelBuilder = new ModelBuilder(resultSet);
            if (resultSet.next()) {
                return modelBuilder.buildMasterObject(true);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }

    List<Category> getMastersCategories(int id) {
        String findMastersServices = "SELECT id, name_ua, name_en FROM masters_services INNER JOIN services_categories " +
                "on masters_services.service_category_id = services_categories.id WHERE master_id=?";
        List<Category> servicesList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(findMastersServices)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            modelBuilder = new ModelBuilder(resultSet);
            while (resultSet.next()) {
                servicesList.add(modelBuilder.buildCategoryObject(false));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
        return servicesList;
    }

    @Override
    public Master get(String email) {
        String findMaster = "SELECT * FROM masters WHERE email=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(findMaster)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            modelBuilder = new ModelBuilder(resultSet);
            if (resultSet.next()) {
                return modelBuilder.buildMasterObject(true);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        String deleteMaster = "DELETE FROM masters WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteMaster)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
    }

    @Override
    public List<Master> listAll() {
        String selectAllMasters = "SELECT * FROM masters";
        List<Master> mastersList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectAllMasters)) {
            ResultSet resultSet = statement.executeQuery();
            modelBuilder = new ModelBuilder(resultSet);
            while (resultSet.next()) {
                mastersList.add(modelBuilder.buildMasterObject(true));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
        return mastersList;
    }

}
