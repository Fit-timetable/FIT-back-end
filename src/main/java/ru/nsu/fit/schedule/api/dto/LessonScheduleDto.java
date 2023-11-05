package ru.nsu.fit.schedule.api.dto;

import ru.nsu.fit.lesson.impl.domain.model.LessonParity;
import ru.nsu.fit.lesson.impl.domain.model.LessonPlace;
import ru.nsu.fit.lesson.impl.domain.model.LessonType;

public record LessonScheduleDto(
    String subject,
    LessonType type,
    String startTime,
    String teacher,
    LessonPlace place,
    LessonParity parity
) {
}
