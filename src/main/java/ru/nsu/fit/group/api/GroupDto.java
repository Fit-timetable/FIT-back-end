package ru.nsu.fit.group.api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Дто группы")
public record GroupDto(
        @Schema(description = "Идентификатор группы") Long id,
        @Schema(description = "Номер группы") String name
) {
}
