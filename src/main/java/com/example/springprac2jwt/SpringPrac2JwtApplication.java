package com.example.springprac2jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringPrac2JwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringPrac2JwtApplication.class, args);
    }

}
