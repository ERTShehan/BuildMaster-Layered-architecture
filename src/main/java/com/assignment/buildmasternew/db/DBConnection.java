package com.assignment.buildmasternew.db;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class DBConnection {
    private static DBConnection dbConnection;
    private final Connection connection;
    private final String URL ="jdbc:mysql://localhost:3306/Buildmaster";
    private final String USER ="root";
    private final String PASSWORD ="Ijse@1234";

    private DBConnection() throws SQLException {
        connection = DriverManager.getConnection(URL,USER,PASSWORD);
    }

    public static DBConnection getInstance() throws SQLException {
        if (dbConnection == null) {
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }

//    public static Connection getConnection() {
//        return (Connection) dbConnection;
//    }
}
