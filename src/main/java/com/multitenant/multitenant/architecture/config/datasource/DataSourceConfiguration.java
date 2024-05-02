package com.multitenant.multitenant.architecture.config.datasource;


import com.multitenant.multitenant.architecture.config.web.ThreadTenantStorage;
import com.multitenant.multitenant.architecture.entities.TenantData;
import com.multitenant.multitenant.architecture.repositories.TenantDataRepository;
import jakarta.annotation.PostConstruct;
import org.flywaydb.core.Flyway;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import javax.sql.DataSource;


@Configuration
public class DataSourceConfiguration {

    private final DataSourceProperties dataSourceProperties;

    public DataSourceConfiguration(DataSourceProperties dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }

    @Bean
    public DataSource dataSource() {

            TenantRoutingDataSource customDataSource = new TenantRoutingDataSource();
            customDataSource.setTargetDataSources(dataSourceProperties.getDatasources());
            return customDataSource;




    }

  //  @PostConstruct
    public void migrate() {
        dataSourceProperties
                .getDatasources()
                .values()
                .stream()
                .map(dataSource -> (DataSource) dataSource)
                .forEach(this::migrate);
    }

    private void migrate(DataSource dataSource) {
        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();
    }
}
