package ru.nsu.fit.schedule.api;

import java.util.List;

public record DayScheduleDto(
    List<LessonScheduleDto> lessons
) {
}
