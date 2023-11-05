package ru.nsu.fit.schedule.port;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import ru.nsu.fit.lesson.impl.domain.model.entities.Lesson;
import ru.nsu.fit.schedule.api.ScheduleService;
import ru.nsu.fit.schedule.api.dto.PinRequestDto;
import ru.nsu.fit.schedule.api.dto.WeekScheduleDto;

import static ru.nsu.fit.schedule.port.ScheduleUrl.*;

@RestController
@AllArgsConstructor
@RequestMapping(SCHEDULE_URL)
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping(RESET_URL)
    public void resetSchedule(@RequestParam("student_id") Long studentId) {
        scheduleService.resetSchedule(studentId);
    }

    @PostMapping(PIN_URL)
    public void pinSchedule(@RequestBody PinRequestDto pinRequestDto) {
        scheduleService.pinSchedule(pinRequestDto.studentId(), pinRequestDto.group());
    }

    @GetMapping(GROUP_URL + "/{group}")
    public WeekScheduleDto getScheduleByGroup(@PathVariable String group){
        return scheduleService.getScheduleByGroup(group);
    }

    @GetMapping(ROOM_URL + "/{room}")
    public WeekScheduleDto getScheduleByRoom(@PathVariable String room){
        return scheduleService.getScheduleByRoom(room);
    }

    @PostMapping(LESSON_URL)
    public Long createLesson(@RequestBody Lesson lesson) {
        return scheduleService.createLesson(lesson).getId();
    }
}
