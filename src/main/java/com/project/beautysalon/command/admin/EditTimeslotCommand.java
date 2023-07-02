package com.project.beautysalon.command.admin;

import com.project.beautysalon.command.Command;
import dao.AppointmentDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditTimeslotCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int appointmentId = Integer.parseInt(request.getParameter("appointmentId"));
        int newAppointmentId = Integer.parseInt(request.getParameter("newAppointmentId"));
        var appointmentDao = new AppointmentDao();
        var appointment = appointmentDao.get(appointmentId);
        int serviceId = appointment.getServiceID();
        int clientId = appointment.getClientID();
        appointment.setStatus("free");
        appointment.setClientID(null);
        appointment.setServiceID(null);
        appointmentDao.update(appointment);
        var newAppointment = appointmentDao.get(newAppointmentId);
        newAppointment.setStatus("booked");
        newAppointment.setClientID(clientId);
        newAppointment.setServiceID(serviceId);
        appointmentDao.update(newAppointment);

        request.getSession().setAttribute("message", "message.appointment-confirmed-new-timeslot");

        return "/app?command=viewAppointmentRequests";
    }
}
