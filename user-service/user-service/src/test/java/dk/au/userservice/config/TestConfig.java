package dk.au.userservice.config;

import dk.au.userservice.utils.UserMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(basePackages = "dk.au.userservice")
public class TestConfig {
    
    @Bean
    public UserMapper userMapper() {
        return new UserMapper();
    }
} 