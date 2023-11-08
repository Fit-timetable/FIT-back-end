package ru.nsu.fit.schedule.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Расписание группы на неделю")
public record WeekScheduleDto(

    @Schema(description = "Список занятий на понедельник")
    DayScheduleDto monday,
    @Schema(description = "Список занятий на вторник")
    DayScheduleDto tuesday,
    @Schema(description = "Список занятий на среду")
    DayScheduleDto wednesday,
    @Schema(description = "Список занятий на четверг")
    DayScheduleDto thursday,
    @Schema(description = "Список занятий на пятницу")
    DayScheduleDto friday,
    @Schema(description = "Список занятий на субботу")
    DayScheduleDto saturday

) {
}
