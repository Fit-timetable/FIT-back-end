package ru.nsu.fit.schedule.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "The DTO represents all lessons for the day in the current group")
public record WeekScheduleDto(
    @ApiModelProperty(notes = "List of lessons for monday")
    DayScheduleDto monday,
    @ApiModelProperty(notes = "List of lessons in tuesday")
    DayScheduleDto tuesday,
    @ApiModelProperty(notes = "List of lessons in wednesday")
    DayScheduleDto wednesday,
    @ApiModelProperty(notes = "List of lessons in thursday")
    DayScheduleDto thursday,
    @ApiModelProperty(notes = "List of lessons in friday")
    DayScheduleDto friday,
    @ApiModelProperty(notes = "List of lessons in saturday")
    DayScheduleDto saturday
) {
}
