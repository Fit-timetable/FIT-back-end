package ru.nsu.fit.schedule.api.dto;

public record WeekScheduleDto(
        DayScheduleDto monday,
        DayScheduleDto tuesday,
        DayScheduleDto wednesday,
        DayScheduleDto thursday,
        DayScheduleDto friday,
        DayScheduleDto saturday
) {
}
