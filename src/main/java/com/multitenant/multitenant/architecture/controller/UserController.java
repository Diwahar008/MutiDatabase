package com.multitenant.multitenant.architecture.controller;

import com.multitenant.multitenant.architecture.config.web.TokenProvider;
import com.multitenant.multitenant.architecture.dto.UserDto;
import com.multitenant.multitenant.architecture.entities.Car;
import com.multitenant.multitenant.architecture.entities.TenantData;
import com.multitenant.multitenant.architecture.entities.User;
import com.multitenant.multitenant.architecture.service.TenantService;
import com.multitenant.multitenant.architecture.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    TenantService tenantService;
    @Autowired
    UserService userService;
    @Autowired
    TokenProvider tokenProvider;

    @PostMapping("/user")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> createUser(@RequestBody UserDto user){

        tenantService.saveTenantInfo(user,user.getName());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("/user/auth")
    public String login(@RequestBody UserDto userLogin){
        User fetchedUser = userService.getUserByName(userLogin.getEmail());
        String token = "";
        if(fetchedUser.getPassword().equals(userLogin.getPassword())){
            TenantData fetchedTenantData = tenantService.getTenantDataById(fetchedUser.getTenantData().getTenantId());
            token = tokenProvider.createToken(userLogin.getEmail(),fetchedTenantData.getTenantName());
        }
        return token;
    }
}
