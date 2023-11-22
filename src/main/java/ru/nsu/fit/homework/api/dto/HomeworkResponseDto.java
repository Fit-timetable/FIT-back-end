package ru.nsu.fit.homework.api.dto;

public record HomeworkResponseDto(
        Long id,
        Long subjectId,
        String dueDate,
        String text,
        Integer estimatedTime,
        String file,
        Boolean isGroupShared,
        Integer notificationPeriod,
        Short notificationDaysBeforeDeadline
) {
}
