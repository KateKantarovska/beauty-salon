package com.project.beautysalon.command.master;

import com.project.beautysalon.command.Command;
import dao.AppointmentDao;
import model.Appointment;
import model.Master;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EditWorkingDaysCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        var appointmentDao = new AppointmentDao();
        var formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
        var timeslots = List.of("10:00", "12:00", "14:00", "16:00");

        int masterId = ((Master) request.getSession().getAttribute("user")).getId();
        var pickedDatesList = request.getParameterMap().keySet().stream()
                .filter(parameter -> parameter.matches("\\d+\\.\\d+\\.\\d+"))
                .map(stringDate -> LocalDate.parse(stringDate, formatter))
                .toList();


        var presentMasterAppointments = appointmentDao.getByMasterId(masterId);
        var immutableDates = presentMasterAppointments.stream()
                .filter(appointment -> !appointment.getStatus().equals("free"))
                .map(Appointment::getDate)
                .distinct()
                .toList();

        presentMasterAppointments.forEach(appointment -> {
            var appointmentDate = appointment.getDate();
            if (!pickedDatesList.contains(appointmentDate) && !immutableDates.contains(appointmentDate)) {
                appointmentDao.delete(appointment.getId());
            }
        });

        var presentMasterAppointmentsDates = presentMasterAppointments.stream()
                .map(Appointment::getDate)
                .distinct()
                .toList();

        pickedDatesList.stream()
                .filter(date -> !presentMasterAppointmentsDates.contains(date))
                .forEach(date -> timeslots.forEach(timeslot -> {
                    var appointment = new Appointment();
                    appointment.setMasterID(masterId);
                    appointment.setDate(date);
                    appointment.setTimeslot(LocalTime.parse(timeslot));
                    appointmentDao.create(appointment);
                }));

        request.getSession().setAttribute("message", "message.changes-saved");
        return "/app?command=viewWorkingDays";
    }
}
