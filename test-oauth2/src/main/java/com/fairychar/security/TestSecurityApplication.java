package com.fairychar.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * Datetime: 2021/3/22 11:45 <br>
 *
 * @author chiyo <br>
 * @since 1.0
 */
@SpringBootApplication
//@EnableResourceServer
public class TestSecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestSecurityApplication.class, args);
    }
}
