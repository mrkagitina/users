package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;


import java.sql.*;
import java.util.Properties;

public class Util {

    private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/kata";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12345";

    private static SessionFactory sessionFactory;

    public static Connection getDBConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = getProperties();
                configuration.setProperties(settings);

                sessionFactory = configuration.addAnnotatedClass(User.class).buildSessionFactory();
            } catch (Exception e) {
                System.out.println("Could not create session factory");
            }
        }
        return sessionFactory;
    }

    private static Properties getProperties() {
        Properties settings = new Properties();

        settings.put(Environment.URL, DB_CONNECTION);
        settings.put(Environment.USER, DB_USER);
        settings.put(Environment.PASS, DB_PASSWORD);
        settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");

        settings.put(Environment.SHOW_SQL, "true");
        settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        settings.put(Environment.HBM2DDL_AUTO, "create-drop");
        return settings;
    }
}

