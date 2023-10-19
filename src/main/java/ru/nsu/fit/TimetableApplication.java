package ru.nsu.fit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.nsu.fit.parser.WebParser;

@SpringBootApplication
public class TimetableApplication {

    public static void main(String[] args) {
        new WebParser();
        SpringApplication.run(TimetableApplication.class, args);
    }

}
