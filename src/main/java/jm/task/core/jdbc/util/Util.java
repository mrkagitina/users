package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {

    private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/kata";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12345";

    static public Connection getDBConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
