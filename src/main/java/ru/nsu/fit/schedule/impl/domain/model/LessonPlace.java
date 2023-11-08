package ru.nsu.fit.schedule.impl.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Описание и формат занятия")
public record LessonPlace(
        @Schema(description = "Номер аудитории") String room,
        @Schema(description = "Ссылка для дистанционного подключения к занятию") String remoteLink
) {
}
