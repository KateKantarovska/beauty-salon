package com.project.beautysalon.command.admin;

import com.project.beautysalon.command.Command;
import dao.ClientDao;
import model.Client;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class ViewClientsCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        ClientDao clientDao = new ClientDao();
        List<Client> clientsList = clientDao.listAll();
        clientsList.sort(Comparator.comparing(Client::getName).thenComparing(Client::getSurname));
        String pageNumber = request.getParameter("page");
        int page;
        if (pageNumber == null) {
            page = 1;
        } else {
            page = Integer.parseInt(pageNumber);
        }
        request.setAttribute("page", page);
        request.setAttribute("clientsList", clientsList);
        try {
            request.getRequestDispatcher("/admin/clients-list").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}
