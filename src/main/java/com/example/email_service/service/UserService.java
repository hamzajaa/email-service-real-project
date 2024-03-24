package com.example.email_service.service;

import com.example.email_service.bean.User;

public interface UserService {
    User saveUser(User user);
    Boolean verifyToken(String token);
}
