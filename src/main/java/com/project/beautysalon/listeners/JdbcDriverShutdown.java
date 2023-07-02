package com.project.beautysalon.listeners;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

@WebListener
public class JdbcDriverShutdown implements ServletContextListener {
    private static final Logger logger = LogManager.getLogger();
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            var driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.log(Level.ERROR, e);
            }
        }
        AbandonedConnectionCleanupThread.checkedShutdown();
    }
}
