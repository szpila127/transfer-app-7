package com.transfer.app7.service;

import com.transfer.app7.domain.Account;
import com.transfer.app7.domain.User;
import com.transfer.app7.repository.AccountRepository;
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
    private AccountRepository accountRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User save(User user) {
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

    public void addUserAccount(final Long userId, final Account account) {
        userRepository.findById(userId).get().getAccounts().add(account);
    }

    public void deleteUserAccount(final Long userId, final Account account) {
        userRepository.findById(userId).get().getAccounts().remove(account);
    }

    public List<Account> getUserAccounts(final Long userId) {
        return userRepository.findById(userId).get().getAccounts();
    }
}
