package com.project.beautysalon.command.master;

import com.project.beautysalon.command.Command;
import com.project.beautysalon.mailing.EmailTopic;
import com.project.beautysalon.mailing.MailSender;
import dao.AppointmentDao;
import dao.ClientDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CompleteAppointmentCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int appointmentId = Integer.parseInt(request.getParameter("id"));
        var appointmentDao = new AppointmentDao();
        var appointment = appointmentDao.get(appointmentId);
        appointment.setStatus("completed");
        appointmentDao.update(appointment);
        request.getSession().setAttribute("message", "message.appointment-completed");

        var clientDao = new ClientDao();
        var client = clientDao.get(appointment.getClientID());
        var mailSender = new MailSender(client, EmailTopic.REVIEW_REQUEST, null);
        mailSender.send();
        return "/app?command=viewBookedAppointments";
    }
}
