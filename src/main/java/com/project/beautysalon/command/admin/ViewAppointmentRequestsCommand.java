package com.project.beautysalon.command.admin;

import com.project.beautysalon.command.Command;
import dao.AppointmentDao;
import dao.ClientDao;
import dao.MasterDao;
import dao.ServiceDao;
import model.Appointment;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class ViewAppointmentRequestsCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        var appointmentDao = new AppointmentDao();
        var serviceDao = new ServiceDao();
        var clientDao = new ClientDao();
        var masterDao = new MasterDao();
        var pendingAppointments = appointmentDao.get("pending");
        pendingAppointments.stream()
                .sorted(Comparator.comparing(Appointment::getDate).thenComparing(Appointment::getTimeslot))
                .forEach(appointment -> {
                    int clientId = appointment.getClientID();
                    int masterId = appointment.getMasterID();
                    int serviceId = appointment.getServiceID();
                    var client = clientDao.get(clientId);
                    var master = masterDao.get(masterId);
                    var service = serviceDao.get(serviceId);
                    appointment.setClientFullname(client.getName() + " " + client.getSurname());
                    appointment.setMasterFullnameEN(master.getNameEN() + " " + master.getSurnameEN());
                    appointment.setMasterFullnameUA(master.getName() + " " + master.getSurname());
                    appointment.setServiceNameEN(service.getNameEN());
                    appointment.setServiceNameUA(service.getNameUA());
                });
        Map<Integer, Map<Integer, String>> freeTimeslots = new TreeMap<>();
        appointmentDao.get("free")
                .stream()
                .sorted(Comparator.comparing(Appointment::getDate).thenComparing(Appointment::getTimeslot))
                .forEach(appointment -> {
            int masterId = appointment.getMasterID();
            freeTimeslots.putIfAbsent(masterId, new TreeMap<>());
            freeTimeslots.get(masterId).put(appointment.getId(), appointment.getDateTimeString());
        });

        request.setAttribute("pendingAppointments", pendingAppointments);
        request.setAttribute("freeTimeslots", freeTimeslots);
        try {
            request.getRequestDispatcher("/jsp/admin/admin-appointment-requests.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}
