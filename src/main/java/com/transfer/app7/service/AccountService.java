package com.transfer.app7.service;

import com.transfer.app7.domain.Account;
import com.transfer.app7.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public Optional<Account> getAccount(final Long id) {
        return accountRepository.findById(id);
    }

    public void deleteAccount(final Long id) {
        accountRepository.deleteById(id);
    }

    public long countAccounts() {
        return accountRepository.count();
    }
}
