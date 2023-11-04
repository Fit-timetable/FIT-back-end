package ru.nsu.fit.schedule.impl.domain.service;

import org.springframework.stereotype.Service;
import ru.nsu.fit.group.impl.domain.model.entities.Group;
import ru.nsu.fit.schedule.impl.domain.model.entities.PinnedSchedule;
import ru.nsu.fit.student.impl.domain.model.entities.Student;

@Service
public class DomainScheduleService {
    public PinnedSchedule createPinnedSchedule(Group group, Student student) {
        PinnedSchedule pinnedSchedule = new PinnedSchedule();
        pinnedSchedule.setGroup(group);
        pinnedSchedule.setStudent(student);
        return pinnedSchedule;
    }
}
