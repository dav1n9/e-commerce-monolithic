package com.example.fastbuymicroservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class FastBuyMicroservicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastBuyMicroservicesApplication.class, args);
    }

}
