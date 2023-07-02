package dao;

import model.Appointment;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDao implements GenericDao<Appointment> {
    private static final Logger logger = LogManager.getLogger();
    private final DataSource dataSource;
    ModelBuilder modelBuilder;

    public AppointmentDao() {
        dataSource = DatabaseUtil.getDataSource();
    }

    public AppointmentDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void create(Appointment appointment) {
        String insertAppointment = "INSERT INTO appointments VALUES(default, default, ?, ?, ?, default, default)";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(insertAppointment, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, appointment.getMasterID());
            statement.setDate(2, Date.valueOf(appointment.getDate()));
            statement.setTime(3, Time.valueOf(appointment.getTimeslot()));
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
    }

    @Override
    public void update(Appointment appointment) {
        String updateAppointment = "UPDATE appointments SET service_id=?, date=?, timeslot=?, status=?, client_id=? WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateAppointment)) {
            if (appointment.getServiceID() != null) {
                statement.setInt(1, appointment.getServiceID());
            } else {
                statement.setNull(1, Types.NULL);
            }
            statement.setDate(2, Date.valueOf(appointment.getDate()));
            statement.setTime(3, Time.valueOf(appointment.getTimeslot()));
            statement.setString(4, appointment.getStatus());
            if (appointment.getClientID() != null) {
                statement.setInt(5, appointment.getClientID());
            } else {
                statement.setNull(5, Types.NULL);
            }
            statement.setInt(6, appointment.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
    }

    @Override
    public Appointment get(Integer id) {
        String getAppointment = "SELECT * FROM appointments WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(getAppointment)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            modelBuilder = new ModelBuilder(resultSet);
            if (resultSet.next()) {
                return modelBuilder.buildAppointmentObject();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }

        return null;
    }



    public List<Appointment> get(String status) {
        String getAppointmentsByStatus = "SELECT * FROM appointments WHERE status=?";
        List<Appointment> appointmentList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(getAppointmentsByStatus)) {
            statement.setString(1, status);
            ResultSet resultSet = statement.executeQuery();
            modelBuilder = new ModelBuilder(resultSet);
            while (resultSet.next()) {
                appointmentList.add(modelBuilder.buildAppointmentObject());
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }

        return appointmentList;
    }

    public List<Appointment> getByClientId (Integer clientId) {
        String getAppointmentsByClient = "SELECT * FROM appointments WHERE client_id=?";
        return getByUser(clientId, getAppointmentsByClient);
    }

    public List<Appointment> getByMasterId (Integer masterId) {
        String getAppointmentsByMaster = "SELECT * FROM appointments WHERE master_id=?";
        return getByUser(masterId, getAppointmentsByMaster);
    }

    private List<Appointment> getByUser(Integer userId, String query) {
        List<Appointment> appointmentList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            modelBuilder = new ModelBuilder(resultSet);
            while (resultSet.next()) {
                appointmentList.add(modelBuilder.buildAppointmentObject());
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
        return appointmentList;
    }

    @Override
    public void delete(Integer id) {
        String deleteAppointment = "DELETE FROM appointments WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteAppointment)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
    }

    @Override
    public List<Appointment> listAll() {
        String selectAllAppointments = "SELECT * FROM appointments";
        List<Appointment> appointmentList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectAllAppointments)) {
            ResultSet resultSet = statement.executeQuery();
            modelBuilder = new ModelBuilder(resultSet);
            while (resultSet.next()) {
                appointmentList.add(modelBuilder.buildAppointmentObject());
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
        return appointmentList;
    }

}
