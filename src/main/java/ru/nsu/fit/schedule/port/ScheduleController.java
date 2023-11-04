package ru.nsu.fit.schedule.port;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.schedule.api.ScheduleService;
import ru.nsu.fit.schedule.api.dto.PinRequestDto;

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
}
