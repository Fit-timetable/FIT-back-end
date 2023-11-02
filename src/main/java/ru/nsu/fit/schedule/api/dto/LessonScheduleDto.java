package ru.nsu.fit.schedule.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.nsu.fit.schedule.impl.domain.model.LessonParity;
import ru.nsu.fit.schedule.impl.domain.model.LessonPlace;
import ru.nsu.fit.schedule.impl.domain.model.LessonType;


@Schema(description = "Информация о занятии в расписании")
public record LessonScheduleDto(
    @Schema(description = "Предмет") String subject,
    @Schema(description = "Тип занятия (Возможные варианты: Лекция, Семинар, Лабораторное занятие)")
    LessonType type,
    @Schema(description = "Время начала занятия в формате ЧЧ:ММ") String startTime,
    @Schema(description = "Фамилия и инциалы преподавателя который ведет занятие")
    String teacher,
    @Schema(description = "Номер аудитории и ссылка для дистанционного подключения")
    LessonPlace place,
    @Schema(description = "Четность недель занятия(Возможные варианты: четное, нечетное, постоянное")
    LessonParity parity
) {
}
