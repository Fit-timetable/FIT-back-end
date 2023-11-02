package ru.nsu.fit.schedule.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.nsu.fit.schedule.impl.domain.model.LessonParity;
import ru.nsu.fit.schedule.impl.domain.model.LessonPlace;
import ru.nsu.fit.schedule.impl.domain.model.LessonType;


@Schema(name = "Информация о занятии в расписании")
public record LessonScheduleDto(
    @Schema(description = "Предмет") String subject,
    @Schema(description = "Тип занятия") LessonType type,
    @Schema(description = "Время начала занятия в формате ЧЧ:ММ") String startTime,
    @Schema(description = "Фамилия и инциалы преподавателя который ведет занятие")
    String teacher,
    @Schema(description = "Место проведения") LessonPlace place,
    @Schema(description = "Четность недель занятия") LessonParity parity
) {
}
