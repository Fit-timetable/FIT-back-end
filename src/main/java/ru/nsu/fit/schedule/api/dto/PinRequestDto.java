package ru.nsu.fit.schedule.api.dto;

public record PinRequestDto(
        Long studentId,
        String group
) {
}
