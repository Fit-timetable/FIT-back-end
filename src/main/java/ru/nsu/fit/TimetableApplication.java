package ru.nsu.fit;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TimetableApplication {
    public static void main(String[] args) {
        SpringApplication.run(TimetableApplication.class, args);
    }
}
