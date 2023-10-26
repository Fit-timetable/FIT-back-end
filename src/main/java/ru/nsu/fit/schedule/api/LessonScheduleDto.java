package ru.nsu.fit.schedule.api;

public record LessonScheduleDto(
    String subject,
    LessonType type,
    String startTime,
    String teacher,
    LessonPlace place,
    LessonParity parity
) {
}
