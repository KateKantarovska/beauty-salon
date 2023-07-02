package dao;

import model.Client;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ClientDao implements UserDao<Client> {
    private static final Logger logger = LogManager.getLogger();
    private final DataSource dataSource;
    ModelBuilder modelBuilder;

    public ClientDao() {
        dataSource = DatabaseUtil.getDataSource();
    }

    public ClientDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void create(Client client) {
        String insertClient = "INSERT INTO clients VALUES(default, ?, ?, ?, ?, ?, default, default)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertClient)) {
            statement.setString(1, client.getEmail());
            statement.setString(2, client.getPassword());
            statement.setString(3, client.getName());
            statement.setString(4, client.getSurname());
            statement.setString(5, client.getPhoneNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
    }

    @Override
    public void update(Client client) {
        String updateClient = "UPDATE clients SET email=?, password=?, name=?, surname=?, phone_number=?, balance=?, active=? WHERE id=?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(updateClient)) {
            statement.setString(1, client.getEmail());
            statement.setString(2, client.getPassword());
            statement.setString(3, client.getName());
            statement.setString(4, client.getSurname());
            statement.setString(5, client.getPhoneNumber());
            statement.setInt(6, client.getBalance());
            statement.setBoolean(7, client.isActive());
            statement.setInt(8, client.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
    }

    @Override
    public Client get(Integer id) {
        String findClient = "SELECT * FROM clients WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(findClient)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            modelBuilder = new ModelBuilder(resultSet);
            if (resultSet.next()) {
                return modelBuilder.buildClientObject();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }

    @Override
    public Client get(String email) {
        String findClient = "SELECT * FROM clients WHERE email=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(findClient)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            modelBuilder = new ModelBuilder(resultSet);
            if (resultSet.next()) {
                return modelBuilder.buildClientObject();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        String deleteClient = "DELETE FROM clients WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteClient)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
    }

    @Override
    public List<Client> listAll() {
        String selectAllClients = "SELECT * FROM clients";
        List<Client> clientsList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectAllClients)) {
            ResultSet resultSet = statement.executeQuery();
            modelBuilder = new ModelBuilder(resultSet);
            while (resultSet.next()) {
                clientsList.add(modelBuilder.buildClientObject());
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
        return clientsList;
    }

}
