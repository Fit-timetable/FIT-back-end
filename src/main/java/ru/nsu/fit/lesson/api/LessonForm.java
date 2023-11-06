package ru.nsu.fit.lesson.api;

import ru.nsu.fit.lesson.impl.domain.model.LessonParity;
import ru.nsu.fit.lesson.impl.domain.model.LessonPlace;
import ru.nsu.fit.lesson.impl.domain.model.LessonType;

public record LessonForm(
    Long studentId,
    Long subjectId,
    LessonType type,
    LessonDate date,
    LessonPlace place,
    LessonParity parity
){}
