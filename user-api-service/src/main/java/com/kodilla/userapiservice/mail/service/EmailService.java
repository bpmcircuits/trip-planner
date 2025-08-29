package com.kodilla.userapiservice.mail.service;

import com.kodilla.userapiservice.exception.EmailSendingException;
import com.kodilla.userapiservice.mail.domain.Mail;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    private MailCreatorService mailCreatorService;

    public void sendVerificationEmail(final Mail mail) throws EmailSendingException {
        try {
            javaMailSender.send(createMimeMessage(mail));
        } catch (MailException e) {
            throw new EmailSendingException("Failed to send email: " + e.getMessage(), e);
        }
    }

    private MimeMessagePreparator createMimeMessage(final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildVerificationCard(mail), true);
        };
    }
}
