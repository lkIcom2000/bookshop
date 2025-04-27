package dk.au.userservice.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"dk.au.userservice"})
public class TestConfig {
    // This class is just to verify component scanning
} 