package com.transfer.app7.scheduler;

import com.transfer.app7.config.EmailConfig;
import com.transfer.app7.domain.Mail;
import com.transfer.app7.service.AccountService;
import com.transfer.app7.service.EmailService;
import com.transfer.app7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {
    private static final String SUBJECT = "TransferApp7: database daily information";

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailConfig emailConfig;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

//    @Scheduled(cron = "0 0 20 * * *")
    @Scheduled(fixedDelay = 10000)
    public void sendInformationEmail() {
        long users = userService.countUsers();
        long accounts = accountService.countAccounts();
        emailService.send(new Mail(
                emailConfig.getAdminMail(),
                SUBJECT,
                "Scheduler"));
    }
}
