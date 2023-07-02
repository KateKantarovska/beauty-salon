package com.project.beautysalon.command.admin;

import com.project.beautysalon.command.Command;
import dao.ServiceDao;
import model.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditServiceCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        var session = request.getSession();
        var serviceDao = new ServiceDao();
        int serviceId = Integer.parseInt(request.getParameter("id"));
        String nameUA = request.getParameter("nameUA");
        String nameEN = request.getParameter("nameEN");
        int priceUAH = Integer.parseInt(request.getParameter("priceUAH"));
        int priceUSD = Integer.parseInt(request.getParameter("priceUSD"));
        Service service = serviceDao.get(nameUA);
        if (service.getId() != serviceId) {
            session.setAttribute("message", "message.service-already-exists");
            return "/app?command=editServiceForm&id=" + serviceId;
        } else {
            service = new Service();
            service.setId(serviceId);
            service.setNameUA(nameUA);
            service.setNameEN(nameEN);
            service.setPriceUAH(priceUAH);
            service.setPriceUSD(priceUSD);
            serviceDao.update(service);
            session.setAttribute("message", "message.service-updated");
            return "/app?command=viewServicesList";
        }
    }
}
