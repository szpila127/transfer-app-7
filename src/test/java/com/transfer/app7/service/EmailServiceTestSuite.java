package com.transfer.app7.service;

import com.transfer.app7.domain.Mail;
import com.transfer.app7.domain.dto.AppEventDto;
import com.transfer.app7.facade.AppEventFacade;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceTestSuite {

    @InjectMocks
    private EmailService emailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private AppEventFacade appEventFacade;

    @Test
    public void shouldSendMail() {
        //Given
        Mail mail = new Mail("test@test.com", "Test", "Test message");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());

        doNothing().when(appEventFacade).createEvent(ArgumentMatchers.any(AppEventDto.class));
        //When
        emailService.send(mail);

        //Then
        verify(javaMailSender, times(1)).send(mailMessage);
    }

    @Test
    public void testCreateMailMessage() {
        //Given
        Mail mail = new Mail("test@test.com", "Test", "Test message");
        //When
        SimpleMailMessage simpleMailMessage = emailService.createMailMessage(mail);
        //Then
        Assert.assertEquals("Test message", simpleMailMessage.getText());
    }
}
