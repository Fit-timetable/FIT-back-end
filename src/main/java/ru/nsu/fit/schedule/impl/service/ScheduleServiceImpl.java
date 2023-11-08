package ru.nsu.fit.schedule.impl.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.group.impl.data.GroupRepository;
import ru.nsu.fit.group.impl.domain.model.Group;
import ru.nsu.fit.schedule.api.ScheduleService;
import ru.nsu.fit.schedule.api.dto.WeekScheduleDto;
import ru.nsu.fit.schedule.impl.data.PinnedScheduleRepository;
import ru.nsu.fit.schedule.impl.domain.model.PinnedSchedule;
import ru.nsu.fit.schedule.impl.domain.service.DomainScheduleService;
import ru.nsu.fit.schedule.impl.domain.service.ScheduleParser;
import ru.nsu.fit.student.impl.data.StudentLessonRepository;
import ru.nsu.fit.student.impl.data.StudentRepository;
import ru.nsu.fit.student.impl.domain.model.Student;
import ru.nsu.fit.student.impl.domain.model.StudentLesson;

import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private DomainScheduleService domainScheduleService;
    private GroupRepository groupRepository;
    private StudentRepository studentRepository;
    private StudentLessonRepository studentLessonRepository;
    private PinnedScheduleRepository pinnedScheduleRepository;

    @Override
    public WeekScheduleDto getScheduleByGroup(String group) {
        return ScheduleParser.parseByGroup(group);
    }

    @Override
    public WeekScheduleDto getScheduleByRoom(String room) {
        return ScheduleParser.parseByRoom(room);
    }

    @Override
    public WeekScheduleDto getPinnedSchedule(Long studentId) {
        PinnedSchedule pinnedSchedule = pinnedScheduleRepository.findByStudentId(studentId).orElseThrow();
        Group group = pinnedSchedule.getGroup();

        WeekScheduleDto weekScheduleDto = getScheduleByGroup(group.getNumber());

        List<StudentLesson> studentLessons = studentLessonRepository.findByStudentId(studentId);

        return ScheduleParser.parseStudentAddedLessons(weekScheduleDto, studentLessons);
    }

    @Override
    public void pinSchedule(Long studentId, String groupNumber) {
        Group group = groupRepository.findByNumber(groupNumber).orElseThrow();
        Student student = studentRepository.findById(studentId).orElseThrow();

        PinnedSchedule pinnedSchedule = domainScheduleService.createPinnedSchedule(group, student);
        pinnedScheduleRepository.save(pinnedSchedule);
    }

    @Override
    public void resetSchedule(Long studentId) {
        PinnedSchedule pinnedSchedule = pinnedScheduleRepository.findByStudentId(studentId).orElseThrow();
        pinnedScheduleRepository.delete(pinnedSchedule);
    }
}
