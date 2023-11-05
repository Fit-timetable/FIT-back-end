package ru.nsu.fit.schedule.api;

import ru.nsu.fit.lesson.impl.domain.model.entities.Lesson;
import ru.nsu.fit.schedule.api.dto.WeekScheduleDto;

public interface ScheduleService {
    WeekScheduleDto getScheduleByGroup(String group);

    WeekScheduleDto getScheduleByRoom(String room);

    void pinSchedule(Long studentId, String group);

    void resetSchedule(Long studentId);

    Lesson createLesson(Lesson lesson);
}
