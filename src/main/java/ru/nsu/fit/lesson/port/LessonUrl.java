package ru.nsu.fit.lesson.port;

public interface LessonUrl {
    String LESSON_URL = "/lesson";
    String LESSON_ID_URL = "/{id}";
    String LESSON_CANCEL_URL = "/{id}/cancel";
    String ID_URL = "/{id}";
}
