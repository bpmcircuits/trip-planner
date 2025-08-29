package com.kodilla.userapiservice.mail.service;

import com.kodilla.userapiservice.config.AdminConfig;
import com.kodilla.userapiservice.mail.domain.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildVerificationCard(Mail mail) {
        Context context = new Context();
        context.setVariable("appName", adminConfig.getAppName());
        context.setVariable("supportEmail", adminConfig.getSupportEmail());
        context.setVariable("userName", mail.getUserName());
        context.setVariable("verificationCode", mail.getVerificationCode());
        context.setVariable("codeExpirationTime", mail.getCodeExpirationTime());
        return templateEngine.process("mail/verification-card-mail", context);
    }
}
