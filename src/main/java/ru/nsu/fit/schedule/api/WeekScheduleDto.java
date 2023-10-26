package ru.nsu.fit.schedule.api;

public record WeekScheduleDto(
    DayScheduleDto monday,
    DayScheduleDto tuesday,
    DayScheduleDto wednesday,
    DayScheduleDto thursday,
    DayScheduleDto friday,
    DayScheduleDto saturday
) {
}
