package ru.nsu.fit.lesson.api.dto;

import ru.nsu.fit.homework.api.dto.HomeworkResponseDto;
import ru.nsu.fit.resource.api.dto.ResourceResponseDto;
import ru.nsu.fit.lesson.impl.domain.model.LessonPlace;

import java.util.List;

public record LessonDetailsDto(
        Long id,
        String startTime,
        String subject,
        LessonPlace place,
        String teacher,
        HomeworkResponseDto upcomingHomework,
        List<ResourceResponseDto> resource
) {
}
