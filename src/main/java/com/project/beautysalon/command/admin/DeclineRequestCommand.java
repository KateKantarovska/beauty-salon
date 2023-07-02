package com.project.beautysalon.command.admin;

import com.project.beautysalon.command.Command;
import dao.AppointmentDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeclineRequestCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int appointmentId = Integer.parseInt(request.getParameter("id"));
        var appointmentDao = new AppointmentDao();
        var appointment = appointmentDao.get(appointmentId);
        appointment.setStatus("free");
        appointment.setServiceID(null);
        appointment.setClientID(null);
        appointmentDao.update(appointment);
        request.getSession().setAttribute("message", "message.appointment-request-declined");
        return "/app?command=viewAppointmentRequests";
    }
}
