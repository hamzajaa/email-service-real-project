package com.example.email_service.dao;

import com.example.email_service.bean.Confirmation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationDao extends JpaRepository<Confirmation, Long> {

    Confirmation findByToken(String token);

}
