package dao;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DatabaseUtil {
    private static final Logger logger = LogManager.getLogger();
    private static DataSource dataSource;
    private static final String JNDI_LOOKUP_SERVICE = "java:comp/env/jdbc/BeautySalonDB";

    private DatabaseUtil() {
        throw new IllegalStateException("Utility class");
    }

    static {
        try {
            Context context = new InitialContext();
            dataSource = ((DataSource) context.lookup(JNDI_LOOKUP_SERVICE));
        } catch (NamingException e) {
            logger.log(Level.ERROR, e);
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

}
