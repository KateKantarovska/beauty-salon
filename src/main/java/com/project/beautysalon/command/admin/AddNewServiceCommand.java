package com.project.beautysalon.command.admin;

import com.project.beautysalon.command.Command;
import dao.ServiceDao;
import model.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddNewServiceCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        var session = request.getSession();
        int categoryId = Integer.parseInt(request.getParameter("id"));
        ServiceDao serviceDao = new ServiceDao();
        String nameUA = request.getParameter("nameUA");
        String nameEN = request.getParameter("nameEN");
        int priceUAH = Integer.parseInt(request.getParameter("priceUAH"));
        int priceUSD = Integer.parseInt(request.getParameter("priceUSD"));
        Service service = serviceDao.get(nameUA);
        if (service != null && service.getNameEN().equals(nameEN)) {
            session.setAttribute("message", "message.service-already-exists");
            return "/jsp/admin/add-new-service.jsp";
        } else {
            service = new Service();
            service.setCategoryId(categoryId);
            service.setNameUA(nameUA);
            service.setNameEN(nameEN);
            service.setPriceUAH(priceUAH);
            service.setPriceUSD(priceUSD);
            serviceDao.create(service);
            session.setAttribute("message", "message.service-created");
            return "/app?command=viewServicesList";
        }
    }
}
