package com.kodilla.tripplanner.amadeus.config;

import com.kodilla.tripplanner.amadeus.client.AmadeusTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class AmadeusApiRestTemplateConfig {

    @Bean
    public RestTemplate amadeusAuthRestTemplate(RestTemplateBuilder b) {
        return b
                .connectTimeout(Duration.ofSeconds(5))
                .readTimeout(Duration.ofSeconds(10))
                .build();
    }

    @Bean
    public RestTemplate amadeusApiRestTemplate(RestTemplateBuilder b, AmadeusTokenManager tokenManager) {
        RestTemplate rt = b
                .connectTimeout(Duration.ofSeconds(5))
                .readTimeout(Duration.ofSeconds(20))
                .build();

        rt.getInterceptors().add((req, body, exec) -> {
            req.getHeaders().setBearerAuth(tokenManager.getValidToken());
            ClientHttpResponse resp = exec.execute(req, body);
            if (resp.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                req.getHeaders().setBearerAuth(tokenManager.getValidToken());
                return exec.execute(req, body);
            }
            return resp;
        });
        return rt;
    }
}