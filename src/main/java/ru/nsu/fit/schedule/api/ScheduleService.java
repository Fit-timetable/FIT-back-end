package ru.nsu.fit.schedule.api;

import ru.nsu.fit.schedule.api.dto.WeekScheduleDto;

public interface ScheduleService {
    WeekScheduleDto getScheduleByGroup(String group);

    WeekScheduleDto getScheduleByRoom(String room);

    WeekScheduleDto getPinnedSchedule(Long studentId);

    void pinSchedule(Long studentId, String group);

    void resetSchedule(Long studentId);
}
