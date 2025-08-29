package com.kodilla.userapiservice;

import com.kodilla.userapiservice.config.DotenvInitializer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = DotenvInitializer.class)
class UserApiServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
