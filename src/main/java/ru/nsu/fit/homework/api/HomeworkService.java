package ru.nsu.fit.homework.api;

import java.time.Duration;
import java.time.ZonedDateTime;

import ru.nsu.fit.homework.api.dto.HomeworkResponseDto;

public interface HomeworkService {
    HomeworkResponseDto getNearestHomeworkResponseDtoByLessonId(Long id);

    public void saveHomework(Long studentId, Long lessonId, ZonedDateTime deadline, Short daysBeforeDeadlineReminder,
                         Duration estimatedTime, String homeworkText, Duration notificationPeriod);
}
