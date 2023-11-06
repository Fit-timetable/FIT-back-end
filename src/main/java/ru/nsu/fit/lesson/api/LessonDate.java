package ru.nsu.fit.lesson.api;

import java.time.LocalDateTime;

import ru.nsu.fit.schedule.impl.domain.model.DayName;

public record LessonDate(
    DayName weekDay,
    LocalDateTime startTime
){}
