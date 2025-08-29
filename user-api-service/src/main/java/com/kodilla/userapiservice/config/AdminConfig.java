package com.kodilla.userapiservice.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AdminConfig {
    @Value("${app.config.appname}")
    private String appName;
    @Value("${app.config.support.email}")
    private String supportEmail;
}
