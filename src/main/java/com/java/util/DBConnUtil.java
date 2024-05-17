package com.java.util;

import com.java.exception.DatabaseConnectionException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnUtil {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/car_rental_system";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "8110019885@Nmk";

    public static Connection getConnection() throws DatabaseConnectionException, SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }
}
