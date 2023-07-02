package com.project.beautysalon.command.client;

import com.project.beautysalon.command.Command;
import dao.ClientDao;
import model.Client;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterClientCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        var session = request.getSession();
        ClientDao clientDao = new ClientDao();
        String firstName = request.getParameter("firstName");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phoneNumber = request.getParameter("phoneNumber");
        Client client = clientDao.get(email);
        if (client != null) {
            session.setAttribute("error", "User with this email already exists");
            return "/registration";
        } else {
            client = new Client();
            client.setEmail(email);
            client.setPassword(DigestUtils.md5Hex(password));
            client.setName(firstName);
            client.setSurname(surname);
            client.setPhoneNumber(phoneNumber);
            client.setBalance(0);
            client.setActive(true);
            clientDao.create(client);
            session.setAttribute("user", client);
            session.setAttribute("role", "client");
            return "/home";
        }
    }

}
