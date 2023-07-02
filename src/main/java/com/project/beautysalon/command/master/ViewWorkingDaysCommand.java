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
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

public class ViewWorkingDaysCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int masterId = ((Master) request.getSession().getAttribute("user")).getId();
        TreeMap<String, String> workingDaysMap = getWorkingDaysMap(masterId);
        request.setAttribute("workingDaysMap", workingDaysMap);
        try {
            request.getRequestDispatcher("/jsp/master/master-working-days.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }

    protected TreeMap<String, String> getWorkingDaysMap(int masterId) {
        AppointmentDao appointmentDao = new AppointmentDao();
        List<Appointment> allAppointments = appointmentDao.listAll()
                .stream()
                .filter(appointment -> appointment.getMasterID() == masterId)
                .toList();
        var workingDays = new TreeMap<String, String>();
        LocalDate date = LocalDate.now();
        LocalDate endDate = date.plusWeeks(1);
        while (date.isBefore(endDate)) {
            LocalDate tempDate = date;
            List<Appointment> timeslots = allAppointments
                    .stream()
                    .filter(appointment -> appointment.getDate().equals(tempDate))
                    .toList();
            workingDays.put(date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)), String.valueOf(!timeslots.isEmpty()));
            date = date.plusDays(1);
        }
        return workingDays;
    }
}
