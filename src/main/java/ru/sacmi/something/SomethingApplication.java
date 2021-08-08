package ru.sacmi.something;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SomethingApplication {
    public static void main(String[] args) {
        SpringApplication.run(SomethingApplication.class, args);
    }

}
