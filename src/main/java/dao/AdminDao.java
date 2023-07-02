package dao;

import model.Admin;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AdminDao implements UserDao<Admin> {
    private static final Logger logger = LogManager.getLogger();
    private final DataSource dataSource;
    ModelBuilder modelBuilder;

    public AdminDao() {
        dataSource = DatabaseUtil.getDataSource();
    }

    public AdminDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void create(Admin admin) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Admin admin) {
        String updateAdmin = "UPDATE admins SET email=?, password=?, name=?, surname=? WHERE id=?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(updateAdmin)) {
            statement.setString(1, admin.getEmail());
            statement.setString(2, admin.getPassword());
            statement.setString(3, admin.getName());
            statement.setString(4, admin.getSurname());
            statement.setInt(5, admin.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
    }

    @Override
    public Admin get(Integer id) {
        return null;
    }

    @Override
    public Admin get(String email) {
        String findAdmin = "SELECT * FROM admins WHERE email=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(findAdmin)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            modelBuilder = new ModelBuilder(resultSet);
            if (resultSet.next()) {
                return modelBuilder.buildAdminObject();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Admin> listAll() {
        throw new UnsupportedOperationException();
    }

}
