package com.project.beautysalon.command.admin;

import com.project.beautysalon.command.Command;
import com.project.beautysalon.mailing.EmailTopic;
import com.project.beautysalon.mailing.MailSender;
import dao.MasterDao;
import model.Master;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterMasterCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        MasterDao masterDao = new MasterDao();
        String email = request.getParameter("email");
        String firstNameUa = request.getParameter("firstNameUa");
        String surnameUa = request.getParameter("surnameUa");
        String firstNameEn = request.getParameter("firstNameEn");
        String surnameEn = request.getParameter("surnameEn");
        String phoneNumber = request.getParameter("phoneNumber");
        Master master = masterDao.get(email);
        if (master != null) {
            request.getSession().setAttribute("message", "message.master-acc-exists");
            return "/admin/master-registration";
        } else {
            master = new Master();
            master.setEmail(email);
            String password = RandomStringUtils.randomAlphanumeric(10);
            master.setPassword(DigestUtils.md5Hex(password));
            master.setName(firstNameUa);
            master.setSurname(surnameUa);
            master.setNameEN(firstNameEn);
            master.setSurnameEN(surnameEn);
            master.setPhoneNumber(phoneNumber);
            masterDao.create(master);

            var mailSender = new MailSender(master, EmailTopic.MASTER_ACCOUNT_CREATED, password);
            mailSender.send();

            return "/app?command=viewMastersList";
        }
    }
}
