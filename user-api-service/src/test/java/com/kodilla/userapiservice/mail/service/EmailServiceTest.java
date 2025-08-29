package com.kodilla.userapiservice.mail.service;

import com.kodilla.userapiservice.exception.EmailSendingException;
import com.kodilla.userapiservice.mail.domain.Mail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    private EmailService emailService;
    
    private Mail testMail;

    @BeforeEach
    void setUp() {
        // Create EmailService with JavaMailSender
        emailService = new EmailService(javaMailSender);
        
        // Create test mail object
        testMail = Mail.builder()
                .mailTo("test@example.com")
                .subject("Test Subject")
                .verificationCode("123456")
                .codeExpirationTime(10)
                .userName("Test User")
                .build();
    }

    @Test
    void shouldSendVerificationEmail() {
        // Given
        doNothing().when(javaMailSender).send(any(MimeMessagePreparator.class));

        // When & Then
        assertDoesNotThrow(() -> emailService.sendVerificationEmail(testMail));
        verify(javaMailSender, times(1)).send(any(MimeMessagePreparator.class));
    }

    @Test
    void shouldThrowExceptionWhenSendingEmailFails() {
        // Given
        doThrow(new MailSendException("Mail sending failed")).when(javaMailSender).send(any(MimeMessagePreparator.class));

        // When & Then
        assertThrows(EmailSendingException.class, () -> emailService.sendVerificationEmail(testMail));
        verify(javaMailSender, times(1)).send(any(MimeMessagePreparator.class));
    }
}