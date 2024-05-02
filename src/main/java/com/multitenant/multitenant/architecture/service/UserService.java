package com.multitenant.multitenant.architecture.service;

import com.multitenant.multitenant.architecture.dto.UserDto;
import com.multitenant.multitenant.architecture.entities.TenantData;
import com.multitenant.multitenant.architecture.entities.User;
import com.multitenant.multitenant.architecture.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public boolean saveUser(UserDto user, TenantData tenantData){
        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setUserName(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setTenantData(tenantData);

        try{
            userRepository.save(newUser);
            return true;
        }catch(Exception exc){
throw new RuntimeException("exception occured");
        }

    }

    public User getUserByName(String userName){
        if(userRepository.findByUserName(userName) == null){
            return null;
        }
        return userRepository.findByUserName(userName);
    }
}
