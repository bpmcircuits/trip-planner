package com.kodilla.nbpapiservice;

import com.kodilla.nbpapiservice.config.DotenvInitializer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = DotenvInitializer.class)
class NbpApiServiceApplicationTests {

    @Test
    void contextLoads() {
        // This test verifies that the application context loads successfully
    }
}