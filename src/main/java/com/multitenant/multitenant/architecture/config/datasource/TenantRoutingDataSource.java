package com.multitenant.multitenant.architecture.config.datasource;


import com.multitenant.multitenant.architecture.config.web.ThreadTenantStorage;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.Map;


public class TenantRoutingDataSource extends AbstractRoutingDataSource {
    @Nullable
    private Map<Object, Object> targetDataSources;
    @Nullable
    private Object defaultTargetDataSource;
    private boolean lenientFallback = true;
    private DataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
 /*   @Nullable
    private Map<Object, DataSource> resolvedDataSources; */
    @Nullable
    private DataSource resolvedDefaultDataSource;


    public void addDataSource(String tenantId, DataSource dataSource) {
        afterPropertiesSet();
 //      getResolvedDataSources().put(tenantId, dataSource);
    }

    @Override
    protected Object determineCurrentLookupKey() {
if(ThreadTenantStorage.getTenantId() == null || ThreadTenantStorage.getTenantId().isEmpty()){
    return null;
}

        return ThreadTenantStorage.getTenantId();
    }
  /*  @Override
    protected DataSource determineTargetDataSource() {
        Assert.notNull(this.resolvedDataSources, "DataSource router not initialized");
        Object lookupKey = this.determineCurrentLookupKey();
        DataSource dataSource = (DataSource)this.resolvedDataSources.get(lookupKey);
        if (dataSource == null && (this.lenientFallback || lookupKey == null)) {
            dataSource = this.resolvedDefaultDataSource;
        }

        if (dataSource == null) {
            // Handle the scenario where no data source is found for the lookup key
            // For example, return a default data source or log a message
            dataSource = this.resolvedDefaultDataSource; // Example: Return a default data source
            // Alternatively, you can log a message
            // logger.warn("No target DataSource found for lookup key [{}]", lookupKey);
        }

        return dataSource;
    } */

}