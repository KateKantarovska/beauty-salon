package com.project.beautysalon.mailing;

import model.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class MailSender {
    private static final Logger logger = LogManager.getLogger();
    private final User user;
    private final EmailTopic emailTopic;
    private String emailSubject;
    private String emailBody;
    private static final Properties properties = new Properties();
    private static Session emailSession;
    private final String additionalInfo;

    static {
        try (InputStream input = new FileInputStream("D:\\Java\\Final task\\beauty-salon\\src\\main\\resources\\mail.properties")) {
            properties.load(input);
            emailSession = Session.getDefaultInstance(properties);
            emailSession.setDebug(true);
        } catch (IOException e) {
            logger.log(Level.ERROR, e);
        }
    }

    public MailSender(User user, EmailTopic emailTopic, String additionalInfo) {
        this.user = user;
        this.emailTopic = emailTopic;
        this.additionalInfo = additionalInfo;
    }

    private void retrieveEmailContent() {
        String username = user.getName().replaceAll("[^A-zА-яіІєЄґҐїЇ]", "");
        Locale locale;
        if (username.matches("[А-яіІєЄґҐїЇ]+")) {
            locale = new Locale("uk");
        } else {
            locale = new Locale("en");
        }
        ResourceBundle resourceBundle = ResourceBundle.getBundle("content", locale);
        emailSubject = resourceBundle.getString(emailTopic.getSubject());
        if (additionalInfo == null) {
            emailBody = resourceBundle.getString(emailTopic.getBody());
        } else {
            emailBody = String.format(resourceBundle.getString(emailTopic.getBody()), additionalInfo);
        }
    }

    private void sendEmail() {
        try {
            retrieveEmailContent();
            Message message = new MimeMessage(emailSession);
            message.setFrom(new InternetAddress(properties.getProperty("mail.smtp.auth.user")));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            message.setSubject(emailSubject);
            message.setContent(emailBody, "text/html; charset=UTF-8");
            Transport transport = emailSession.getTransport();
            transport.connect(properties.getProperty("mail.smtp.host"),
                    Integer.parseInt(properties.getProperty("mail.smtp.port")),
                    properties.getProperty("mail.smtp.auth.user"),
                    properties.getProperty("mail.smtp.auth.pass"));
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            logger.log(Level.ERROR, e);
        }
    }

    public void send() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                sendEmail();
            }
        };
        timer.schedule(task, emailTopic.getDelay());
    }

}
