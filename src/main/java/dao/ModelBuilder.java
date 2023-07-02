package dao;

import model.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public record ModelBuilder(ResultSet resultSet) {

    Client buildClientObject() throws SQLException {
        Client client = new Client();
        client.setId(resultSet.getInt(1));
        client.setEmail(resultSet.getString(2));
        client.setPassword(resultSet.getString(3));
        client.setName(resultSet.getString(4));
        client.setSurname(resultSet.getString(5));
        client.setPhoneNumber(resultSet.getString(6));
        client.setBalance(resultSet.getInt(7));
        client.setActive(resultSet.getBoolean(8));
        return client;
    }

    Category buildCategoryObject(boolean setServicesList) throws SQLException {
        Category category = new Category();
        category.setId(resultSet.getInt(1));
        category.setNameUA(resultSet.getString(2));
        category.setNameEN(resultSet.getString(3));
        if (setServicesList) {
            category.setServices(new CategoryDao().getServicesOfCategory(category.getId()));
        }
        return category;
    }

    Master buildMasterObject(boolean setCategoriesList) throws SQLException {
        Master master = new Master();
        master.setId(resultSet.getInt(1));
        master.setEmail(resultSet.getString(2));
        master.setPassword(resultSet.getString(3));
        master.setName(resultSet.getString(4));
        master.setSurname(resultSet.getString(5));
        master.setNameEN(resultSet.getString(6));
        master.setSurnameEN(resultSet.getString(7));
        master.setPhoneNumber(resultSet.getString(8));
        master.setRating(resultSet.getDouble(9));
        master.setActive(resultSet.getBoolean(10));
        if (setCategoriesList) {
            master.setCategories(new MasterDao().getMastersCategories(master.getId()));
        }
        return master;
    }

    Service buildServiceObject(boolean setMastersList) throws SQLException {
        Service service = new Service();
        service.setId(resultSet.getInt(1));
        service.setCategoryId(resultSet.getInt(2));
        service.setNameUA(resultSet.getString(3));
        service.setNameEN(resultSet.getString(4));
        service.setPriceUAH(resultSet.getInt(5));
        service.setPriceUSD(resultSet.getInt(6));
        if (setMastersList) {
            service.setMastersList(new ServiceDao().listMasters(service.getId()));
        }
        return service;
    }

    Admin buildAdminObject() throws SQLException {
        Admin admin = new Admin();
        admin.setId(resultSet.getInt(1));
        admin.setEmail(resultSet.getString(2));
        admin.setPassword(resultSet.getString(3));
        admin.setName(resultSet.getString(4));
        admin.setSurname(resultSet.getString(5));
        return admin;
    }

    Appointment buildAppointmentObject() throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setId(resultSet.getInt(1));
        appointment.setServiceID(resultSet.getInt(2));
        appointment.setMasterID(resultSet.getInt(3));
        appointment.setDate(resultSet.getDate(4).toLocalDate());
        appointment.setTimeslot(resultSet.getTime(5).toLocalTime());
        appointment.setStatus(resultSet.getString(6));
        appointment.setClientID(resultSet.getInt(7));
        return appointment;
    }

    Review buildReviewObject() throws SQLException {
        Review review = new Review();
        review.setId(resultSet.getInt(1));
        review.setClientID(resultSet.getInt(2));
        review.setMasterID(resultSet.getInt(3));
        review.setText(resultSet.getString(4));
        review.setRating(resultSet.getInt(5));
        review.setVisible(resultSet.getBoolean(6));
        return review;
    }
}
