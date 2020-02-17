package com.transfer.app7.service;

import com.transfer.app7.domain.Event;
import com.transfer.app7.domain.Mail;
import com.transfer.app7.domain.dto.AppEventDto;
import com.transfer.app7.facade.AppEventFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private AppEventFacade appEventFacade;

    public void send(final Mail mail) {
        LOGGER.info("Starting mail preparation...");
        try {
            javaMailSender.send(createMailMessage(mail));
            appEventFacade.createEvent(new AppEventDto(
                    Event.SEND_MAIL,
                    "Mail to: " + mail.getMailTo() + ", subject: " + mail.getSubject()));
            LOGGER.info("Email has been sent.");
        } catch (MailException e) {
            LOGGER.error("Failed  to process email sending: ", e.getMessage(), e);
        }
    }

    public SimpleMailMessage createMailMessage(final Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        return mailMessage;
    }
}
