package com.project.beautysalon;

import com.project.beautysalon.command.Command;
import com.project.beautysalon.command.Commands;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Controller", urlPatterns = "/app")
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            String commandName = request.getParameter("command");
            Command command = Commands.NAME_COMMAND_MAP.get(commandName);
            String address = command.execute(request, response);
            if (address != null) {
                response.sendRedirect(request.getContextPath() + address);
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, e);
        }
    }
}
