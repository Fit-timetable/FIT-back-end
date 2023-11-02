package ru.nsu.fit.schedule.impl.domain.service;

import org.springframework.stereotype.Service;
import ru.nsu.fit.group.impl.domain.model.data.entities.Group;
import ru.nsu.fit.lesson.impl.domain.model.data.entities.Lesson;
import ru.nsu.fit.schedule.impl.domain.model.data.entities.*;
import ru.nsu.fit.student.impl.domain.model.data.entities.Student;
import ru.nsu.fit.student.impl.domain.model.data.entities.StudentLesson;

@Service
public class DomainScheduleService {
    public PinnedSchedule createPinnedSchedule(Group group, Student student) {
        PinnedSchedule pinnedSchedule = new PinnedSchedule();
        pinnedSchedule.setGroup(group);
        pinnedSchedule.setStudent(student);
        return pinnedSchedule;
    }

    public StudentLesson createStudentLesson(Student student, Lesson lesson) {
        StudentLesson studentLesson = new StudentLesson();
        studentLesson.setStudent(student);
        studentLesson.setLesson(lesson);
        studentLesson.setVisited(true);
        return studentLesson;
    }
}
