package com.multitenant.multitenant.architecture.repositories;

import com.multitenant.multitenant.architecture.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserName(String email);
}
