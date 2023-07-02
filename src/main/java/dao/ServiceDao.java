package dao;

import model.Master;
import model.Service;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDao implements GenericDao<Service> {
    private static final Logger logger = LogManager.getLogger();
    private final DataSource dataSource;
    private ModelBuilder modelBuilder;

    public ServiceDao() {
        dataSource = DatabaseUtil.getDataSource();
    }

    public ServiceDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void create(Service service) {
        String insertService = "INSERT INTO services VALUES(default, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertService, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, service.getCategoryId());
            statement.setString(2, service.getNameUA());
            statement.setString(3, service.getNameEN());
            statement.setInt(4, service.getPriceUAH());
            statement.setInt(5, service.getPriceUSD());
            statement.executeUpdate();
            ResultSet generatedKey = statement.getGeneratedKeys();
            if (generatedKey.next()) {
                service.setId(generatedKey.getInt(1));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
    }

    @Override
    public void update(Service service) {
        String updateService = "UPDATE services SET name_ua=?, name_en=?, price_uah=?, price_usd=? WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateService)) {
            statement.setString(1, service.getNameUA());
            statement.setString(2, service.getNameEN());
            statement.setInt(3, service.getPriceUAH());
            statement.setInt(4, service.getPriceUSD());
            statement.setInt(5, service.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
    }

    @Override
    public Service get(Integer id) {
        String findService = "SELECT * FROM services WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(findService)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            modelBuilder = new ModelBuilder(resultSet);
            if (resultSet.next()) {
                return modelBuilder.buildServiceObject(true);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }

    public Service get(String nameUA) {
        String findService = "SELECT * FROM services WHERE name_ua=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(findService)) {
            statement.setString(1, nameUA);
            ResultSet resultSet = statement.executeQuery();
            modelBuilder = new ModelBuilder(resultSet);
            if (resultSet.next()) {
                return modelBuilder.buildServiceObject(true);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        String deleteService = "DELETE FROM services WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteService)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
    }

    @Override
    public List<Service> listAll() {
        String selectAllServices = "SELECT * FROM services";
        List<Service> servicesList= new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectAllServices)) {
            ResultSet resultSet = statement.executeQuery();
            modelBuilder = new ModelBuilder(resultSet);
            while (resultSet.next()) {
                servicesList.add(modelBuilder.buildServiceObject(true));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
        return servicesList;
    }

    List<Master> listMasters(int serviceId) {
        String selectMasters = "SELECT masters.id, masters.email, masters.password, masters.name_ua, masters.surname_ua, masters.name_en, masters.surname_en, masters.phone_number, masters.rating, masters.active FROM services " +
                "INNER JOIN services_categories ON services.category_id=services_categories.id " +
                "INNER JOIN masters_services ON services_categories.id=masters_services.service_category_id " +
                "INNER JOIN masters ON masters_services.master_id=masters.id " +
                "WHERE services.id=?";
        List<Master> mastersList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectMasters)) {
            statement.setInt(1, serviceId);
            ResultSet resultSet = statement.executeQuery();
            modelBuilder = new ModelBuilder(resultSet);
            while (resultSet.next()) {
                mastersList.add(modelBuilder.buildMasterObject(false));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
        return mastersList;
    }
}
