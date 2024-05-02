package com.multitenant.multitenant.architecture.service;

import com.multitenant.multitenant.architecture.config.datasource.DataSourceConfiguration;
import com.multitenant.multitenant.architecture.config.datasource.DataSourceProperties;
import com.multitenant.multitenant.architecture.config.datasource.TenantRoutingDataSource;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

@Component
public class DatabaseService {
    @Autowired
    DataSourceProperties dataSourceProperties;
   // private ConfigurableApplicationContext context;
   @Autowired
   private TenantRoutingDataSource tenantRoutingDataSource;
    private final DataSourceConfiguration dataSourceConfiguration;

    @Autowired
    public DatabaseService(DataSourceConfiguration dataSourceConfiguration) {
        this.dataSourceConfiguration = dataSourceConfiguration;
    }




    public void createTenantSchema(String tenantName){
        String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String password = "root";


        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {
            // Create database
            String sql = "CREATE DATABASE " + tenantName;
            statement.executeUpdate(sql);
            //add datasource to datasources map
         /*  DataSourceProperties dbProp = new DataSourceProperties();
            dbProp.initializeDatasources(); */

            DataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://127.0.0.1:3306/" + tenantName)
                    .driverClassName("com.mysql.cj.jdbc.Driver")
                    .username(user)
                    .password(password)
                    .build();
            dataSourceProperties.datasources.put(tenantName,dataSource);
            tenantRoutingDataSource.addDataSource(tenantName, dataSource);
            //dataSourceConfiguration.dataSource();
           // DataSource dataSources = context.getBean(DataSource.class);
            migrate(dataSource);
            System.out.println("Database schema created successfully.");
        } catch (SQLException e) {
            System.err.println("Error creating database schema: " + e.getMessage());


        }
    }

    public void migrate(Map<Object, Object> dataSources) {
        dataSources.values().forEach(dataSource -> migrate((DataSource) dataSource));
    }

    private void migrate(DataSource dataSource) {
        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();
    }
}
