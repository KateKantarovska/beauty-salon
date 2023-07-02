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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

public class ViewScheduleAdminCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        var appointmentDao = new AppointmentDao();
        var masterDao = new MasterDao();
        var serviceDao = new ServiceDao();
        var clientDao = new ClientDao();
        Map<String, Map<String, List<Appointment>>> appointmentMap = new TreeMap<>();
        var appointmentList = appointmentDao.listAll();
        appointmentList
                .stream()
                .filter(appointment -> !appointment.getDate().isBefore(LocalDate.now()))
                .sorted(Comparator.comparing(Appointment::getDate).thenComparing(Appointment::getTimeslot))
                .map(appointment -> {
                    appointment.setDateString(appointment.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
                    appointment.setTimeslotString(appointment.getTimeslot().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
                    if (!appointment.getStatus().equals("free")) {
                        var master = masterDao.get(appointment.getMasterID());
                        appointment.setMasterFullnameUA(master.getFullnameUA());
                        appointment.setMasterFullnameEN(master.getFullnameEN());
                        var service = serviceDao.get(appointment.getServiceID());
                        appointment.setServiceNameUA(service.getNameUA());
                        appointment.setServiceNameEN(service.getNameEN());
                        var client = clientDao.get(appointment.getClientID());
                        appointment.setClientFullname(client.getName() + " " + client.getSurname());
                    }
                    return appointment;
                })
                .forEach(appointment -> {
                    String dateString = appointment.getDateString();
                    String timeslotString = appointment.getTimeslotString();
                    appointmentMap.putIfAbsent(dateString, new TreeMap<>());
                    appointmentMap.get(dateString).putIfAbsent(timeslotString, new ArrayList<>());
                    if (!appointment.getStatus().equals("free")) {
                        appointmentMap.get(dateString).get(timeslotString).add(appointment);
                    }
                });
        request.setAttribute("appointmentMap", appointmentMap);
        try {
            request.getRequestDispatcher("/jsp/admin/admin-schedule.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}
