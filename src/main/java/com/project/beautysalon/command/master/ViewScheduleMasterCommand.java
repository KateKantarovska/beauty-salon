package com.project.beautysalon.command.master;

import com.project.beautysalon.command.Command;
import dao.AppointmentDao;
import model.Appointment;
import model.Master;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class ViewScheduleMasterCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        var session = request.getSession();
        var master = (Master) session.getAttribute("user");
        int masterId = master.getId();
        var appointmentDao = new AppointmentDao();
        var masterAppointmentsList = appointmentDao.getByMasterId(masterId)
                .stream()
                .filter(appointment -> !appointment.getDate().isBefore(LocalDate.now()))
                .sorted(Comparator.comparing(Appointment::getDate).thenComparing(Appointment::getTimeslot))
                .toList();
        Map<String, Map<String, String>> masterAppointmentsMap = new TreeMap<>();
        masterAppointmentsList.forEach(appointment -> {
            String dateString = appointment.getDateString();
            masterAppointmentsMap.putIfAbsent(dateString, new TreeMap<>());
            masterAppointmentsMap.get(dateString).put(appointment.getTimeslotString(), appointment.getStatus());
        });
        request.setAttribute("masterAppointmentsMap", masterAppointmentsMap);
        try {
            request.getRequestDispatcher("/jsp/master/master-schedule.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}
