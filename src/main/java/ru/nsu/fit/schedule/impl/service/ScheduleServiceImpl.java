package ru.nsu.fit.schedule.impl.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.group.impl.domain.model.entities.Group;
import ru.nsu.fit.group.impl.domain.model.repositories.GroupRepository;
import ru.nsu.fit.schedule.api.ScheduleService;
import ru.nsu.fit.schedule.api.dto.WeekScheduleDto;
import ru.nsu.fit.schedule.impl.domain.model.entities.PinnedSchedule;
import ru.nsu.fit.schedule.impl.domain.model.repositories.PinnedScheduleRepository;
import ru.nsu.fit.schedule.impl.domain.service.DomainScheduleService;
import ru.nsu.fit.student.impl.domain.model.entities.Student;
import ru.nsu.fit.student.impl.domain.model.repositories.StudentRepository;

@Service
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private DomainScheduleService domainScheduleService;
    private GroupRepository groupRepository;
    private StudentRepository studentRepository;
    private PinnedScheduleRepository pinnedScheduleRepository;

    @Override
    public WeekScheduleDto getScheduleByGroup(String group) {
        return null;
    }

    @Override
    public WeekScheduleDto getScheduleByRoom(String room) {
        return null;
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
