package com.multitenant.multitenant.architecture.repositories;


import com.multitenant.multitenant.architecture.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
