package com.kodilla.userapiservice.service;

import com.kodilla.userapiservice.mail.domain.Mail;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendVerificationEmail(final Mail mail) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mail.getMailTo());
            message.setSubject(mail.getSubject());
            message.setText(mail.getMessage());
            javaMailSender.send(message);
        } catch (MailException e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage(), e);
        }
    }
}
