package com.kodilla.nbpapiservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NbpApiServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NbpApiServiceApplication.class, args);
    }

}
