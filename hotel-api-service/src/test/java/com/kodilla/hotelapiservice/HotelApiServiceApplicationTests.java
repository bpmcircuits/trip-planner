package com.kodilla.hotelapiservice;

import com.kodilla.hotelapiservice.config.DotenvInitializer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = DotenvInitializer.class)
class HotelApiServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
