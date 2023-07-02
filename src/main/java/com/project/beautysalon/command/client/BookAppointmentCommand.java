package com.project.beautysalon.command.client;

import com.project.beautysalon.command.Command;
import dao.AppointmentDao;
import dao.ClientDao;
import dao.ServiceDao;
import model.Client;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BookAppointmentCommand implements Command {
    private static final String MESSAGE = "message";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String url = "/app?" + request.getParameter("queryString");
        var session = request.getSession();
        var client = (Client) request.getSession().getAttribute("user");
        int serviceId = Integer.parseInt(request.getParameter("serviceId"));
        var clientDao = new ClientDao();
        var serviceDao = new ServiceDao();
        var appointmentDao = new AppointmentDao();
        int appointmentId = Integer.parseInt(request.getParameter("appointmentId"));
        var appointment = appointmentDao.get(appointmentId);
        if (client == null) {
            session.setAttribute(MESSAGE, "message.sign-in-needed");
            return url;
        } else if (client.getBalance() < serviceDao.get(serviceId).getPriceUAH()) {
            session.setAttribute(MESSAGE, "message.insufficient-funds");
            return url;
        } else {
            String dateAndTime = String.valueOf(appointment.getDate()) + appointment.getTimeslot();
            boolean clientHasAppointments = appointmentDao.getByClientId(client.getId()).stream().filter(ap -> !ap.getStatus().equals("free")).map(ap -> String.valueOf(ap.getDate()) + ap.getTimeslot()).toList().contains(dateAndTime);
            if (clientHasAppointments) {
                session.setAttribute(MESSAGE, "message.timeslot-is-booked");
                return url;
            } else {
                appointment.setStatus("pending");
                appointment.setClientID(client.getId());
                appointment.setServiceID(serviceId);
                appointmentDao.update(appointment);
                client.setBalance(client.getBalance() - serviceDao.get(serviceId).getPriceUAH());
                clientDao.update(client);
                session.setAttribute("user", client);
                return "/success";
            }
        }
    }
}
