package com.multitenant.multitenant.architecture.controller;

import com.multitenant.multitenant.architecture.dto.ProductDto;
import com.multitenant.multitenant.architecture.dto.UserDto;
import com.multitenant.multitenant.architecture.service.ProductService;
import com.multitenant.multitenant.architecture.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/product")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> createProduct(@RequestBody ProductDto product){

        productService.createProduct(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
