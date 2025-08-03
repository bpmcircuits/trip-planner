package com.kodilla.tripplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TripPlannerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TripPlannerApplication.class, args);
    }

}
