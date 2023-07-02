package com.project.beautysalon.command.admin;

import com.project.beautysalon.command.Command;
import dao.AppointmentDao;
import dao.ClientDao;
import dao.ServiceDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CancelAppointmentCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int appointmentId = Integer.parseInt(request.getParameter("id"));
        var appointmentDao = new AppointmentDao();
        var clientDao = new ClientDao();
        var serviceDao = new ServiceDao();
        var appointment = appointmentDao.get(appointmentId);
        var client = clientDao.get(appointment.getClientID());
        var service = serviceDao.get(appointment.getServiceID());
        client.setBalance(client.getBalance() + service.getPriceUAH());
        clientDao.update(client);
        appointment.setStatus("free");
        appointment.setClientID(null);
        appointment.setServiceID(null);
        appointmentDao.update(appointment);

        request.getSession().setAttribute("message", "message.appointment-canceled");
        return "/app?command=viewSchedule";
    }
}
