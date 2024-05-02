package com.multitenant.multitenant.architecture.repositories;


import com.multitenant.multitenant.architecture.entities.TenantData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface TenantDataRepository extends JpaRepository<TenantData, Integer> {
}
