package dao;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;

public class TestDatabaseUtil {
    private static final BasicDataSource dataSource = new BasicDataSource();

    private TestDatabaseUtil() {
        throw new IllegalStateException("Utility class");
    }

    static {
        dataSource.setUrl("jdbc:mysql://localhost:3306/beauty_salon_test");
        dataSource.setUsername("root");
        dataSource.setPassword("1111");
    }

    static DataSource getDataSource() {
        return dataSource;
    }

}
