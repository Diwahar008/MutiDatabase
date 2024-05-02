package com.multitenant.multitenant.architecture.repositories;

import com.multitenant.multitenant.architecture.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
