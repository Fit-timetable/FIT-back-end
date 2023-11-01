package ru.nsu.fit.schedule.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ru.nsu.fit.schedule.impl.domain.model.LessonParity;
import ru.nsu.fit.schedule.impl.domain.model.LessonPlace;
import ru.nsu.fit.schedule.impl.domain.model.LessonType;


@ApiModel(description = "DTO representing information about lesson in timetable")
public record LessonScheduleDto(
    @ApiModelProperty(notes = "Subject of the lesson")
    String subject,
    @ApiModelProperty(notes = "Type of the lesson (Possible options: LECTURE, SEMINAR, LABORATORY)")
    LessonType type,
    @ApiModelProperty(notes = "Lesson start time in HH:MM")
    String startTime,
    @ApiModelProperty(notes = "Teacher leading the lesson in name and initials")
    String teacher,
    @ApiModelProperty(notes = "Classroom number and link for connection")
    LessonPlace place,
    @ApiModelProperty(notes = "Parity of the lesson(Possible options: ODD, EVEN, ALWAYS)")
    LessonParity parity
) {
}
