package com.kodilla.userapiservice.mail.service;

import com.kodilla.userapiservice.config.AdminConfig;
import com.kodilla.userapiservice.mail.domain.Mail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MailCreatorServiceTest {

    @Mock
    private AdminConfig adminConfig;

    @Mock
    private TemplateEngine templateEngine;

    @InjectMocks
    private MailCreatorService mailCreatorService;

    private Mail testMail;

    @BeforeEach
    void setUp() {
        testMail = Mail.builder()
                .mailTo("test@example.com")
                .subject("Test Subject")
                .verificationCode("123456")
                .codeExpirationTime(10)
                .userName("Test User")
                .build();
    }

    @Test
    void shouldBuildVerificationCard() {
        // Given
        when(adminConfig.getAppName()).thenReturn("Trip Planner");
        when(adminConfig.getSupportEmail()).thenReturn("support@tripplanner.com");
        when(templateEngine.process(eq("mail/verification-card-mail"), any(Context.class)))
                .thenReturn("<html>Test Email</html>");

        // When
        String result = mailCreatorService.buildVerificationCard(testMail);

        // Then
        assertEquals("<html>Test Email</html>", result);
        verify(adminConfig, times(1)).getAppName();
        verify(adminConfig, times(1)).getSupportEmail();
        verify(templateEngine, times(1)).process(eq("mail/verification-card-mail"), any(Context.class));
    }
}