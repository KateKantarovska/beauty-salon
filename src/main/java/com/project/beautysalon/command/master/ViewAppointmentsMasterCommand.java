package com.project.beautysalon.command.master;

import com.project.beautysalon.command.Command;
import dao.AppointmentDao;
import dao.ClientDao;
import dao.ServiceDao;
import model.Appointment;
import model.Master;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class ViewAppointmentsMasterCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int masterId = ((Master) request.getSession().getAttribute("user")).getId();
        var appointmentDao = new AppointmentDao();
        var clientDao = new ClientDao();
        var serviceDao = new ServiceDao();
        var mastersBookedAppointments = appointmentDao.get("booked")
                .stream()
                .filter(appointment -> appointment.getMasterID().equals(masterId))
                .toList();
        var bookedAppointments = new ArrayList<>(mastersBookedAppointments);
        bookedAppointments.sort(Comparator.comparing(Appointment::getDate).thenComparing(Appointment::getTimeslot));
        bookedAppointments.forEach(appointment -> {
            int clientId = appointment.getClientID();
            int serviceId = appointment.getServiceID();
            var client = clientDao.get(clientId);
            var service = serviceDao.get(serviceId);
            appointment.setClientFullname(client.getName() + " " + client.getSurname());
            appointment.setServiceNameEN(service.getNameEN());
            appointment.setServiceNameUA(service.getNameUA());
        });
        request.setAttribute("bookedAppointments", bookedAppointments);
        try {
            request.getRequestDispatcher("/jsp/master/master-booked-appointments.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}
