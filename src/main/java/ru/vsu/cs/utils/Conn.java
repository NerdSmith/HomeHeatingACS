package ru.vsu.cs.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conn {
    private static final String url = "jdbc:postgresql://localhost:5432/hheatingdb";
    private static final String username = "hheatingjava";
    private static final String password = "hheatingpass";

    private static Connection connection = null;

    public static Connection getConn() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(url, username, password);
        }
        return connection;
    }
}
