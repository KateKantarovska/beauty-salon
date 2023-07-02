package com.project.beautysalon.command.common;

import com.project.beautysalon.command.Command;
import dao.AdminDao;
import dao.ClientDao;
import dao.MasterDao;
import model.User;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginCommand implements Command {
    private static final String MESSAGE = "message";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        var session = request.getSession();
        String email = request.getParameter("email");
        String password = DigestUtils.md5Hex(request.getParameter("password"));
        System.out.println(password);
        User user = findUser(email);
        if (user == null) {
            session.setAttribute(MESSAGE, "message.user-doesnt-exist");
            return "/sign-in";
        } else if (!user.getPassword().equals(password)) {
            session.setAttribute(MESSAGE, "message.incorrect-password");
            return "/sign-in";
        } else if (user.isActive() != null && !user.isActive()) {
                session.setAttribute(MESSAGE, "message.account-blocked");
            return "/sign-in";
        } else {
            String role = user.getClass().toString().toLowerCase().split("\\.")[1];
            session.setAttribute("user", user);
            session.setAttribute("role", role);
            return "/home";
        }

    }

    public User findUser (String email) {
        var clientDao = new ClientDao();
        var masterDao = new MasterDao();
        var adminDao = new AdminDao();
        if (clientDao.get(email) != null) {
            return clientDao.get(email);
        } else if (masterDao.get(email) != null) {
            return masterDao.get(email);
        } else if (adminDao.get(email) != null) {
            return adminDao.get(email);
        } else {
            return null;
        }
    }

}
