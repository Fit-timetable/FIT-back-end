package ru.nsu.fit.schedule.impl.service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.group.impl.domain.model.data.entities.Group;
import ru.nsu.fit.group.impl.domain.model.data.repositories.GroupRepository;
import ru.nsu.fit.lesson.impl.domain.model.data.entities.Lesson;
import ru.nsu.fit.lesson.impl.domain.model.data.repositories.LessonRepository;
import ru.nsu.fit.schedule.api.ScheduleService;
import ru.nsu.fit.schedule.api.dto.WeekScheduleDto;
import ru.nsu.fit.schedule.impl.domain.model.data.entities.*;
import ru.nsu.fit.schedule.impl.domain.model.data.repositories.*;
import ru.nsu.fit.schedule.impl.domain.service.DomainScheduleService;
import ru.nsu.fit.student.impl.domain.model.data.entities.Student;
import ru.nsu.fit.student.impl.domain.model.data.entities.StudentLesson;
import ru.nsu.fit.student.impl.domain.model.data.repositories.StudentLessonRepository;
import ru.nsu.fit.student.impl.domain.model.data.repositories.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private DomainScheduleService domainScheduleService;
    private GroupRepository groupRepository;
    private StudentRepository studentRepository;
    private PinnedScheduleRepository pinnedScheduleRepository;
    private StudentLessonRepository studentLessonRepository;
    private LessonRepository lessonRepository;

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
        Optional<Group> group = groupRepository.findByNumber(groupNumber);
        if (group.isEmpty()) {
            throw new IllegalArgumentException("Group not found");
        }

        Student student = studentRepository.findById(studentId).orElseThrow();
        PinnedSchedule pinnedSchedule = domainScheduleService.createPinnedSchedule(group.get(), student);
        pinnedScheduleRepository.save(pinnedSchedule);

        List<Lesson> lessons = lessonRepository.findByGroup_Id(group.get().getId());

        for (Lesson lesson : lessons) {
            if (studentLessonRepository.findByStudentAndLesson(student, lesson).isEmpty()) {
                StudentLesson studentLesson = domainScheduleService.createStudentLesson(student, lesson);
                studentLessonRepository.save(studentLesson);
            }
        }
    }

    @Override
    public void resetSchedule(Long studentId) {
        List<StudentLesson> studentLessons = studentLessonRepository.findByStudent_Id(studentId);
        studentLessonRepository.deleteAll(studentLessons);
    }
}
