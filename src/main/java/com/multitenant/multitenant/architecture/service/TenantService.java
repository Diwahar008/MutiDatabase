package com.multitenant.multitenant.architecture.service;

import com.multitenant.multitenant.architecture.dto.UserDto;
import com.multitenant.multitenant.architecture.entities.TenantData;
import com.multitenant.multitenant.architecture.repositories.TenantDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TenantService {
    @Autowired
    TenantDataRepository tenantDataRepository;
    @Value("${spring.datasource.username}")
    private String dbUser;

    @Value("${spring.datasource.password}")
    private String dbPassword;
    @Autowired
    UserService userService;
    @Autowired
    DatabaseService databaseService;
    @Transactional
    public boolean saveTenantInfo(UserDto user, String tenantName){

        TenantData tenant = new TenantData();
        tenant.setTenantName(tenantName);
        tenant.setDbName(tenantName);
        tenant.setDbUserName(dbUser);
        tenant.setDbPassword(dbPassword);

        try{
            tenant = tenantDataRepository.save(tenant);
            userService.saveUser(user,tenant);
            databaseService.createTenantSchema(tenantName);
        }catch (Exception exc){
            throw new RuntimeException("exception occured");
        }
return true;
    }

    public TenantData getTenantDataById(Integer tenantId){
        Optional<TenantData> fetchedTenantData = tenantDataRepository.findById(tenantId);
        if(!fetchedTenantData.isEmpty()){
            return fetchedTenantData.get();
        }
        return null;
    }
}
