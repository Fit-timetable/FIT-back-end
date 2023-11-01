package ru.nsu.fit.schedule.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
@ApiModel(description = "The DTO represents all lessons for the day in the current group")
public record DayScheduleDto(
    @ApiModelProperty(notes = "The list of lessons for the day")
    List<LessonScheduleDto> lessons
) {
}
