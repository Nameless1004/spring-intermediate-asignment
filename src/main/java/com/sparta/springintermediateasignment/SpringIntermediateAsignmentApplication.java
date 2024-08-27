package com.sparta.springintermediateasignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableCaching
@SpringBootApplication
public class SpringIntermediateAsignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringIntermediateAsignmentApplication.class, args);
    }

}
