package com.multitenant.multitenant.architecture.config.datasource;


import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {

    public static void main(String[] args) {
        // JDBC connection parameters
        String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String password = "root";
        String databaseName = "test_1";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {
            // Create database
            String sql = "CREATE DATABASE " + databaseName;
            statement.executeUpdate(sql);
            System.out.println("Database schema created successfully.");
        } catch (SQLException e) {
            System.err.println("Error creating database schema: " + e.getMessage());


        }
    }
}
