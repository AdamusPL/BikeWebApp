package com.bikeparadise.bikewebapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BikeWebAppConfig {
    @Bean
    public PasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }
}
