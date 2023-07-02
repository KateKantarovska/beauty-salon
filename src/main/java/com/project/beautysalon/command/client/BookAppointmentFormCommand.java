package com.project.beautysalon.command.client;

import com.project.beautysalon.command.Command;
import dao.AppointmentDao;
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
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class BookAppointmentFormCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        var masterDao = new MasterDao();
        var serviceDao = new ServiceDao();
        var appointmentDao = new AppointmentDao();
        int masterId = Integer.parseInt(request.getParameter("masterId"));
        int serviceId = Integer.parseInt(request.getParameter("serviceId"));
        var master = masterDao.get(masterId);
        var service = serviceDao.get(serviceId);
        Map<Integer, String> freeTimeslots = new TreeMap<>();
        appointmentDao.get("free")
                .stream().filter(appointment -> {
                    LocalDateTime dateTime = LocalDateTime.of(appointment.getDate(), appointment.getTimeslot());
                    return appointment.getMasterID() == masterId
                            && dateTime.isAfter(LocalDateTime.now());
                })
                .sorted(Comparator.comparing(Appointment::getDate).thenComparing(Appointment::getTimeslot))
                .forEach(appointment -> freeTimeslots.put(appointment.getId(), appointment.getDateTimeString()));
        request.setAttribute("master", master);
        request.setAttribute("service", service);
        request.setAttribute("freeTimeslots", freeTimeslots);

        try {
            request.getRequestDispatcher("/jsp/client/book-appointment-form.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}
