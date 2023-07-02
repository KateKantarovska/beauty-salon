package com.project.beautysalon.command.common;

import com.project.beautysalon.command.Command;
import dao.AdminDao;
import dao.ClientDao;
import dao.MasterDao;
import model.Admin;
import model.Client;
import model.Master;
import model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditProfileCommand implements Command {

    private HttpSession session;
    private User user;
    private int userId;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phoneNumber;
    private static final String MESSAGE = "message";
    private static final String MESSAGE_CHANGES_SAVED = "message.changes-saved";
    private static final String MESSAGE_USER_EXISTS = "message.user-already-exists";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        session = request.getSession();
        String userRole = session.getAttribute("role").toString();
        user = ((User) session.getAttribute("user"));
        userId = user.getId();
        password = user.getPassword();
        name = request.getParameter("firstName");
        surname = request.getParameter("surname");
        email = request.getParameter("email");
        phoneNumber = request.getParameter("phoneNumber");
        return
            switch (userRole) {
                case "client" -> editClientProfile();
                case "master" -> editMasterProfile();
                case "admin" -> editAdminProfile();
                default -> null;
            };
    }

    private String editClientProfile() {
        ClientDao clientDao = new ClientDao();
        Client client = clientDao.get(email);
        if (client != null && client.getId() != userId) {
            session.setAttribute(MESSAGE, MESSAGE_USER_EXISTS);
        } else {
            client = ((Client) user);
            client.setName(name);
            client.setSurname(surname);
            client.setEmail(email);
            client.setPhoneNumber(phoneNumber);
            clientDao.update(client);
            session.setAttribute("user", client);
            session.setAttribute(MESSAGE, MESSAGE_CHANGES_SAVED);
        }
        return "/client/account";
    }

    private String editMasterProfile() {
        MasterDao masterDao = new MasterDao();
        Master master = masterDao.get(email);
        if (master != null && master.getId() != userId) {
            session.setAttribute(MESSAGE, MESSAGE_USER_EXISTS);
        } else {
            master = ((Master) user);
            master.setEmail(email);
            master.setPhoneNumber(phoneNumber);
            masterDao.update(master);
            session.setAttribute("user", master);
            session.setAttribute(MESSAGE, MESSAGE_CHANGES_SAVED);
        }
        return "/master/account";
    }

    private String editAdminProfile() {
        AdminDao adminDao = new AdminDao();
        Admin admin = adminDao.get(email);
        if (admin != null && admin.getId() != userId) {
            session.setAttribute(MESSAGE, MESSAGE_USER_EXISTS);
        } else {
            admin = new Admin();
            admin.setId(userId);
            admin.setEmail(email);
            admin.setPassword(password);
            admin.setName(name);
            admin.setSurname(surname);
            adminDao.update(admin);
            session.setAttribute("user", admin);
            session.setAttribute(MESSAGE, MESSAGE_CHANGES_SAVED);
        }
        return "/admin/account";
    }


}
