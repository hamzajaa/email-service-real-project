package com.example.email_service.dao;

import com.example.email_service.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
    User findByEmailIgnoreCase(String email);
    Boolean existsByEmail(String email);
}