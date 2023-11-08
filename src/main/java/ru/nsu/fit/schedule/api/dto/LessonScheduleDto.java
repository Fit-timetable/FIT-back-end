package ru.nsu.fit.schedule.api.dto;


import ru.nsu.fit.lesson.impl.domain.model.LessonParity;
import ru.nsu.fit.lesson.impl.domain.model.LessonPlace;
import ru.nsu.fit.lesson.impl.domain.model.LessonType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Информация о занятии в расписании")
public record LessonScheduleDto(
    Long id,
    @Schema(description = "Предмет") String subject,
    @Schema(description = "Тип занятия") LessonType type,
    @Schema(description = "Время начала занятия в формате ЧЧ:ММ") String startTime,
    @Schema(description = "Фамилия и инциалы преподавателя который ведет занятие")
    String teacher,
    @Schema(description = "Место проведения") LessonPlace place,
    @Schema(description = "Четность недель занятия") LessonParity parity

) {
}
