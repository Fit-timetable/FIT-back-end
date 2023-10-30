package ru.nsu.fit.schedule.impl.service;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.fit.schedule.api.ScheduleService;
import ru.nsu.fit.schedule.api.dto.WeekScheduleDto;
import ru.nsu.fit.schedule.impl.domain.service.ScheduleParser;

@Service
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private final ScheduleParser scheduleParser;

    @Override
    public WeekScheduleDto getScheduleByGroup(String group) {
        return null;
    }

    @Override
    public WeekScheduleDto getScheduleByRoom(String room) {
        return null;
    }

    @Override
    public void pinSchedule(Long studentId, String group) {
        scheduleParser.pinSchedule(studentId, group);
    }

    @Override
    public void resetSchedule(Long studentId) {
        scheduleParser.resetSchedule(studentId);
    }
}
