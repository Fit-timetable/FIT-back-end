package ru.nsu.fit.lesson.api.dto;

import ru.nsu.fit.lesson.api.LessonDate;
import ru.nsu.fit.lesson.impl.domain.model.LessonPlace;

public record EditLessonDto(
    Long subjectId,
    LessonDate date,
    String teacher,
    LessonPlace place
) {}
