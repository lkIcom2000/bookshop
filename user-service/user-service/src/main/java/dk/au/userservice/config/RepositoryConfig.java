package dk.au.userservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "dk.au.userservice.repo")
public class RepositoryConfig {
    // No need for explicit bean definition, @EnableJpaRepositories will handle it
} 