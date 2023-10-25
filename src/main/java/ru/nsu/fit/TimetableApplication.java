package ru.nsu.fit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ru.nsu.fit.parse.schedule.group.ScheduleParser;


@SpringBootApplication
public class TimetableApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimetableApplication.class, args);

        ScheduleParser scheduleParser = new ru.nsu.fit.parse.schedule.group.ScheduleParser();
        ru.nsu.fit.parse.schedule.auditorium.ScheduleParser auditoriumParser = new ru.nsu.fit.parse.schedule.auditorium.ScheduleParser();

        scheduleParser.setGROUP_NUMBER(20209);
        scheduleParser.parse();        
        
    }
}
