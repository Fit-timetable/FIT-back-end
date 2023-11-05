package ru.nsu.fit.schedule.api;

import ru.nsu.fit.lesson.impl.domain.model.LessonForm;
import ru.nsu.fit.lesson.impl.domain.model.entities.Lesson;
import ru.nsu.fit.schedule.api.dto.WeekScheduleDto;

public interface ScheduleService {
    WeekScheduleDto getScheduleByGroup(String group);

    WeekScheduleDto getScheduleByRoom(String room);

    WeekScheduleDto getPinnedSchedule(Long studentId);

    void pinSchedule(Long studentId, String group);

    void resetSchedule(Long studentId);

    Lesson createLesson(LessonForm lessonForm);
}
