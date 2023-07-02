package com.project.beautysalon.command.common;

import com.project.beautysalon.command.Command;
import dao.AdminDao;
import dao.ClientDao;
import dao.MasterDao;
import model.Admin;
import model.Client;
import model.Master;
import model.User;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class ChangePasswordCommand implements Command {

    private HttpSession session;
    private User user;
    private String enteredCurrentPassword;
    private String currentPassword;
    private String newPassword;
    private static final String ALERT = "alert";
    private static final String INCORRECT_PASSWORD = "message.incorrect-password";
    private static final String PASSWORD_UPDATED = "message.password-updated";


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        session = request.getSession();
        String userRole = session.getAttribute("role").toString();
        user = ((User) session.getAttribute("user"));
        currentPassword = user.getPassword();
        enteredCurrentPassword = DigestUtils.md5Hex(request.getParameter("current-password"));
        newPassword = DigestUtils.md5Hex(request.getParameter("new-password"));

        return switch (userRole) {
            case "client" -> updatePasswordClient();
            case "master" -> updatePasswordMaster();
            case "admin" -> updatePasswordAdmin();
            default -> null;
        };
    }

    private String updatePasswordClient() {
        if (!enteredCurrentPassword.equals(currentPassword)) {
            session.setAttribute(ALERT, INCORRECT_PASSWORD);
        } else {
            ClientDao clientDao = new ClientDao();
            Client client = ((Client) user);
            client.setPassword(newPassword);
            clientDao.update(client);
            session.setAttribute("user", client);
            session.setAttribute(ALERT, PASSWORD_UPDATED);
        }
        return "/client/account";
    }

    private String updatePasswordMaster() {
        if (!enteredCurrentPassword.equals(currentPassword)) {
            session.setAttribute(ALERT, INCORRECT_PASSWORD);
        } else {
            MasterDao masterDao = new MasterDao();
            Master master = ((Master) user);
            master.setPassword(newPassword);
            masterDao.update(master);
            session.setAttribute("user", master);
            session.setAttribute(ALERT, PASSWORD_UPDATED);
        }
        return "/master/account";
    }

    private String updatePasswordAdmin() {
        if (!enteredCurrentPassword.equals(currentPassword)) {
            session.setAttribute(ALERT, INCORRECT_PASSWORD);
        } else {
            AdminDao adminDao = new AdminDao();
            Admin admin = (Admin) user;
            admin.setPassword(newPassword);
            adminDao.update(admin);
            session.setAttribute("user", admin);
            session.setAttribute(ALERT, PASSWORD_UPDATED);
        }
        return "/admin/account";
    }
}
