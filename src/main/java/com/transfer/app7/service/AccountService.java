package com.transfer.app7.service;

import com.transfer.app7.config.EmailConfig;
import com.transfer.app7.domain.Account;
import com.transfer.app7.domain.Mail;
import com.transfer.app7.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailConfig emailConfig;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account save(Account account, boolean newAccount) {
        if (newAccount) {
            emailService.send(new Mail(
                    emailConfig.getAdminMail(),
                    "New account :)",
                    "New account for: " + account.getUser() + " has bee created on your application."
            ));
        }
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
