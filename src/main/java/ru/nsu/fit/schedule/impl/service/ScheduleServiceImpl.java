package ru.nsu.fit.schedule.impl.service;

import lombok.AllArgsConstructor;

import java.time.ZonedDateTime;

import org.springframework.stereotype.Service;
import ru.nsu.fit.group.impl.domain.model.entities.Group;
import ru.nsu.fit.group.impl.domain.model.repositories.GroupRepository;
import ru.nsu.fit.lesson.impl.domain.model.LessonForm;
import ru.nsu.fit.lesson.impl.domain.model.entities.Lesson;
import ru.nsu.fit.lesson.impl.domain.model.repositories.LessonRepository;
import ru.nsu.fit.schedule.api.ScheduleService;
import ru.nsu.fit.schedule.api.dto.WeekScheduleDto;
import ru.nsu.fit.schedule.impl.domain.model.entities.PinnedSchedule;
import ru.nsu.fit.schedule.impl.domain.model.repositories.PinnedScheduleRepository;
import ru.nsu.fit.schedule.impl.domain.service.DomainScheduleService;
import ru.nsu.fit.schedule.impl.domain.service.ScheduleParser;
import ru.nsu.fit.student.impl.domain.model.entities.Student;
import ru.nsu.fit.student.impl.domain.model.repositories.StudentRepository;

@Service
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private DomainScheduleService domainScheduleService;
    private GroupRepository groupRepository;
    private StudentRepository studentRepository;
    private PinnedScheduleRepository pinnedScheduleRepository;
    private LessonRepository lessonRepository;

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
        return getScheduleByGroup(group.getNumber());
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

    @Override
    public Lesson createLesson(LessonForm lessonForm) {
        Lesson lesson = new Lesson();
        lesson.setDayName(lessonForm.date().weekDay());
        lesson.setStartTime(ZonedDateTime.parse(lessonForm.date().startTime()));
        lesson.setRoom(lessonForm.place().room());
        lesson.setTeacher(null);
        lesson.setMeetLink(lessonForm.place().meetLink());
        lesson.setLessonType(lessonForm.type());
        lesson.setLessonParity(lessonForm.parity());
        lesson.setSubject(null); 
        lesson.setGroup(null);

        return lessonRepository.save(lesson);
    }
}
