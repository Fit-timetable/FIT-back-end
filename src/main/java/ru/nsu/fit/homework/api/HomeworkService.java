package ru.nsu.fit.homework.api;

import ru.nsu.fit.homework.api.dto.HomeworkResponseDto;

public interface HomeworkService {
    HomeworkResponseDto getNearestHomeworkResponseDtoByLessonId(Long id);
}
