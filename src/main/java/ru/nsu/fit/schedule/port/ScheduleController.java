package ru.nsu.fit.schedule.port;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import ru.nsu.fit.schedule.api.ScheduleService;
import ru.nsu.fit.schedule.api.dto.PinRequestDto;
import ru.nsu.fit.schedule.api.dto.WeekScheduleDto;
import ru.nsu.fit.student.api.StudentService;

import static ru.nsu.fit.schedule.port.ScheduleUrl.*;

@RestController
@AllArgsConstructor
@RequestMapping(SCHEDULE_URL)
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final StudentService studentService;

    @PostMapping(RESET_URL)
    public void resetSchedule() {
        var student = studentService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.UNAUTHORIZED));
        scheduleService.resetSchedule(student.getId());
    }

    @PostMapping(PIN_URL)
    public void pinSchedule(@RequestBody PinRequestDto pinRequestDto) {
        var student = studentService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.UNAUTHORIZED));
        scheduleService.pinSchedule(student.getId(), pinRequestDto.group());
    }

    @GetMapping(PIN_URL)
    public WeekScheduleDto getPinnedSchedule() {
        var student = studentService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.UNAUTHORIZED));
        return scheduleService.getPinnedSchedule(student.getId());
    }

    @GetMapping(GROUP_URL)
    public WeekScheduleDto getScheduleByGroup(@PathVariable String group) {
        return scheduleService.getScheduleByGroup(group);
    }

    @GetMapping(ROOM_URL)
    public WeekScheduleDto getScheduleByRoom(@PathVariable String room) {
        return scheduleService.getScheduleByRoom(room);
    }
}
