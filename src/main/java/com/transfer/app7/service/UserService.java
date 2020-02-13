package com.transfer.app7.service;

import com.transfer.app7.config.EmailConfig;
import com.transfer.app7.domain.Mail;
import com.transfer.app7.domain.User;
import com.transfer.app7.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailConfig emailConfig;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User save(User user) {
        emailService.send(new Mail(
                emailConfig.getAdminMail(),
                "New user :)",
                "New user: " + user.getEmail() + " has bee created on your application."
        ));
        return userRepository.save(user);
    }

    public Optional<User> getUser(final Long id) {
        return userRepository.findById(id);
    }

    public void deleteUser(final Long id) {
        userRepository.deleteById(id);
    }

    public long countUsers() {
        return userRepository.count();
    }
}
