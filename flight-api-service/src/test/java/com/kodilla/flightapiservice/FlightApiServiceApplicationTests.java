package com.kodilla.flightapiservice;

import com.kodilla.flightapiservice.config.DotenvInitializer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = DotenvInitializer.class)
class FlightApiServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
