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
import java.util.List;
import java.util.TreeMap;

public class EditWorkingDaysFormCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int masterId = ((Master) request.getSession().getAttribute("user")).getId();
        var appointmentDao = new AppointmentDao();
        List<Appointment> allMasterAppointments = appointmentDao.listAll()
                .stream()
                .filter(appointment -> appointment.getMasterID() == masterId)
                .toList();
        TreeMap<String, String> workingDaysMap = new TreeMap<>();
        LocalDate date = LocalDate.now();
        LocalDate endDate = date.plusWeeks(1);
        while (date.isBefore(endDate)) {
            LocalDate tempDate = date;
            List<String> appointmentStatusesList = allMasterAppointments
                    .stream()
                    .filter(appointment -> appointment.getDate().equals(tempDate))
                    .map(Appointment::getStatus)
                    .toList();
            String state;
            if (appointmentStatusesList.isEmpty()) {
                state = "";
            } else if (appointmentStatusesList.contains("pending")
                    || appointmentStatusesList.contains("booked")
                    || appointmentStatusesList.contains("completed")) {
                state = "checked disabled";
            } else {
                state = "checked";
            }
            workingDaysMap.put(date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)), state);
            date = date.plusDays(1);
        }
        request.setAttribute("workingDaysMap", workingDaysMap);
        try {
            request.getRequestDispatcher("/jsp/master/edit-working-days.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}
