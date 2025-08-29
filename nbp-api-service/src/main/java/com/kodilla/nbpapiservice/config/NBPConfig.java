package com.kodilla.nbpapiservice.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class NBPConfig {

    @Value("${nbp.api.endpoint.prod}")
    private String nbpApiEndpoint;
}
