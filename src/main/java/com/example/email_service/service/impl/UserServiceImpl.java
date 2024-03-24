package com.example.email_service.service.impl;

import com.example.email_service.bean.Confirmation;
import com.example.email_service.bean.User;
import com.example.email_service.dao.ConfirmationDao;
import com.example.email_service.dao.UserDao;
import com.example.email_service.service.EmailService;
import com.example.email_service.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private ConfirmationDao confirmationDao;
    @Autowired
    private EmailService emailService;

    @Override
    @Transactional
    public User saveUser(User user) {
        if (userDao.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exist");
        }
        user.setEnabled(false);
        User savedUser = userDao.save(user);

        Confirmation confirmation = new Confirmation(savedUser);
        confirmationDao.save(confirmation);

        /* TODO Send email to usr with token */
//        emailService.sendSimpleEmailMessage(user.getName(), user.getEmail(), confirmation.getToken());
//        emailService.sendMimeMessageWithAttachments(user.getName(), user.getEmail(), confirmation.getToken());
//        emailService.sendMimeMessageWithEmbeddedFiles(user.getName(), user.getEmail(), confirmation.getToken()); // the different is when I send the email to the outlook => the photo and the word is shoe before enter into email
        emailService.sendHtmlEmail(user.getName(), user.getEmail(), confirmation.getToken()); // the different is when I send the email to the outlook => the photo and the word is shoe before enter into email
//        emailService.sendHtmlEmailWithEmbeddedFiles(user.getName(), user.getEmail(), confirmation.getToken()); // the different is when I send the email to the outlook => the photo and the word is shoe before enter into email

        return savedUser;
    }

    @Override
    public Boolean verifyToken(String token) {
        Confirmation confirmation = confirmationDao.findByToken(token);
        User user = userDao.findByEmailIgnoreCase(confirmation.getUser().getEmail());
        user.setEnabled(true);
        userDao.save(user);
        //confirmationDao.delete(confirmation);
        return true;
    }
}
