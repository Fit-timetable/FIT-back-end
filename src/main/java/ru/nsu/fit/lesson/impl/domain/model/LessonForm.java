package ru.nsu.fit.lesson.impl.domain.model;

public record LessonForm(
    Long studentId,
    Long subjectId,
    LessonType type,
    LessonDate date,
    LessonPlace place,
    LessonParity parity
){}
