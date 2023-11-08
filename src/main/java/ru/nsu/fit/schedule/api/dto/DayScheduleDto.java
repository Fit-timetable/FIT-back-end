package ru.nsu.fit.schedule.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
@Schema(name = "Информация о предметах за день")
public record DayScheduleDto(
    @Schema(description = "Список предметов") List<LessonScheduleDto> lessons

) {
}
