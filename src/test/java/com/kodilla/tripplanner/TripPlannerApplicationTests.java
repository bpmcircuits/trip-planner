package com.kodilla.tripplanner;

import com.kodilla.tripplanner.config.DotenvInitializer;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = DotenvInitializer.class)
class TripPlannerApplicationTests {

    @Test
    void contextLoads() {
    }

}
