package com.project.beautysalon.command.client;

import com.project.beautysalon.command.Command;
import dao.AppointmentDao;
import dao.MasterDao;
import dao.ServiceDao;
import model.Appointment;
import model.Client;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;

public class ViewAppointmentsClientCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        var session = request.getSession();
        var client = ((Client) session.getAttribute("user"));
        var appointmentDao = new AppointmentDao();
        var serviceDao = new ServiceDao();
        var masterDao = new MasterDao();
        var appointments = appointmentDao.getByClientId(client.getId());
        appointments.stream()
                .sorted(Comparator.comparing(Appointment::getDate).thenComparing(Appointment::getTimeslot))
                .forEach(appointment -> {
                    var master = masterDao.get(appointment.getMasterID());
                    appointment.setMasterFullnameEN(master.getFullnameEN());
                    appointment.setMasterFullnameUA(master.getFullnameUA());
                    var service = serviceDao.get(appointment.getServiceID());
                    appointment.setServiceNameEN(service.getNameEN());
                    appointment.setServiceNameUA(service.getNameUA());
                });

        request.setAttribute("appointmentsList", appointments);
        try {
            request.getRequestDispatcher("/jsp/client/client-appointments.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}
