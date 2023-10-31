package ru.nsu.fit.schedule.port;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.schedule.api.ScheduleService;

@RestController
@AllArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/reset/{studentId}")
    public void resetSchedule(@PathVariable("studentId") long studentId) {
        scheduleService.resetSchedule(studentId);
    }

    @PostMapping("/pin/{studentId}/{group}")
    public void pinSchedule(@PathVariable("studentId") long studentId, @PathVariable("group") String group) {
        scheduleService.pinSchedule(studentId, group);
    }
}
