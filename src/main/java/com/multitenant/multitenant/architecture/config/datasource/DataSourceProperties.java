package com.multitenant.multitenant.architecture.config.datasource;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class DataSourceProperties {
    public final Map<Object, Object> datasources;

    public DataSourceProperties() {
        this.datasources = new LinkedHashMap<>();
    }

    @PostConstruct
    public void initializeDatasources() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/", "root", "root");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT db_name, db_user_name, db_password FROM master_db.tenant_data");
            while (resultSet.next()) {
                String tenantDb = resultSet.getString("db_name");
                String username = resultSet.getString("db_user_name");
                String password = resultSet.getString("db_password");

                DataSource dataSource = DataSourceBuilder.create()
                        .url("jdbc:mysql://127.0.0.1:3306/" + tenantDb)
                        .driverClassName("com.mysql.cj.jdbc.Driver")
                        .username(username)
                        .password(password)
                        .build();

                datasources.put(tenantDb, dataSource);
            }

            DataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://127.0.0.1:3306/" + "master_db")
                    .driverClassName("com.mysql.cj.jdbc.Driver")
                    .username("root")
                    .password("root")
                    .build();
            datasources.put("master_db", dataSource);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Object, Object> getDatasources() {
        return datasources;
    }
}
