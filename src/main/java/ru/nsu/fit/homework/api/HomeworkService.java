package ru.nsu.fit.homework.api;

import ru.nsu.fit.homework.api.dto.HomeworkResponseDto;
import ru.nsu.fit.homework.impl.domain.model.Homework;

public interface HomeworkService {
    HomeworkResponseDto getNearestHomeworkResponseDtoByLessonId(Long id);

    void saveHomework(Homework homework);
}
