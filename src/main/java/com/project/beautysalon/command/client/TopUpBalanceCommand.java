package com.project.beautysalon.command.client;

import com.project.beautysalon.command.Command;
import dao.ClientDao;
import model.Client;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TopUpBalanceCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        ClientDao clientDao = new ClientDao();
        Client client = (Client) session.getAttribute("user");
        int sumToAdd = Integer.parseInt(request.getParameter("sum"));
        client.setBalance(client.getBalance() + sumToAdd);
        clientDao.update(client);
        session.setAttribute("message", "message.balance-replenished");
        return "/client/balance";
    }
}
