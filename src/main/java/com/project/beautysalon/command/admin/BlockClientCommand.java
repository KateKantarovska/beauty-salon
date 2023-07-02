package com.project.beautysalon.command.admin;

import com.project.beautysalon.command.Command;
import dao.ClientDao;
import model.Client;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BlockClientCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        ClientDao clientDao = new ClientDao();
        int id = Integer.parseInt(request.getParameter("id"));
        Client client = clientDao.get(id);
        client.setActive(false);
        clientDao.update(client);

        return "/app?command=viewClientsList";
    }
}
