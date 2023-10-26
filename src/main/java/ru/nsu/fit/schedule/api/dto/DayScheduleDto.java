package ru.nsu.fit.schedule.api.dto;

import java.util.List;

public record DayScheduleDto(
    List<LessonScheduleDto> lessons
) {
}
