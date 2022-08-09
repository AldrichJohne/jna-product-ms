package com.ajru.pharmacy_product_system.auth.repository;

import com.ajru.pharmacy_product_system.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserName(String username);
}
