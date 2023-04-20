package com.example.fundoonotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication

public class FundoonotesApplication {

    public static void main(String[] args) {
        SpringApplication.run(FundoonotesApplication.class, args);
    }

}
