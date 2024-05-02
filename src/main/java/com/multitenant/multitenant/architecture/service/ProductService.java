package com.multitenant.multitenant.architecture.service;

import com.multitenant.multitenant.architecture.dto.ProductDto;
import com.multitenant.multitenant.architecture.entities.Product;
import com.multitenant.multitenant.architecture.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public boolean createProduct(ProductDto productDto){
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());

        try{
            productRepository.save(product);
        }catch(Exception exc){

        }

        return true;
    }

}
