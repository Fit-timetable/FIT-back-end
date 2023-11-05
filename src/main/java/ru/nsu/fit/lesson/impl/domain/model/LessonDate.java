package ru.nsu.fit.lesson.impl.domain.model;

import ru.nsu.fit.schedule.impl.domain.model.DayName;

public record LessonDate(
    DayName weekDay,
    String startTime
){}
