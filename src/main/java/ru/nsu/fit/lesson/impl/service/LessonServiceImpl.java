package ru.nsu.fit.lesson.impl.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.homework.api.dto.HomeworkResponseDto;
import ru.nsu.fit.homework.impl.service.HomeworkService;
import ru.nsu.fit.lesson.api.LessonForm;
import ru.nsu.fit.lesson.api.LessonService;
import ru.nsu.fit.lesson.api.dto.LessonDetailsDto;
import ru.nsu.fit.lesson.impl.data.LessonRepository;
import ru.nsu.fit.lesson.impl.domain.model.entities.Lesson;
import ru.nsu.fit.lesson.impl.domain.service.DomainLessonService;
import ru.nsu.fit.lesson.impl.domain.service.LessonParser;
import ru.nsu.fit.resource.api.dto.ResourceResponseDto;
import ru.nsu.fit.resource.impl.service.ResourceService;
import ru.nsu.fit.student.api.StudentService;
import ru.nsu.fit.subject.api.SubjectService;
import ru.nsu.fit.subject.impl.domain.model.Subject;

import java.util.List;

@Service
@AllArgsConstructor
public class LessonServiceImpl implements LessonService {
    private LessonRepository lessonRepository;
    private SubjectService subjectService;
    private StudentService studentService;
    private HomeworkService homeworkService;
    private ResourceService resourceService;
    private LessonParser lessonParser;

    @Override
    public Lesson createLesson(LessonForm lessonForm) {
        Subject subject = subjectService.getSubject(lessonForm.subjectId());
        Lesson lesson = lessonRepository.save(DomainLessonService.mapping(lessonForm, subject));

        studentService.saveStudentLesson(DomainLessonService.mapping(lesson, studentService.getStudent(lessonForm.studentId())));

        return lesson;
    }

    @Override
    public LessonDetailsDto getLesson(Long id) {
        Lesson lesson = lessonRepository.getReferenceById(id);
        HomeworkResponseDto homeworkResponseDto = homeworkService.getHomeworkResponseDtoByLessonId(id);
        List<ResourceResponseDto> resources = resourceService.getResourcesDtoBySubjectId(lesson.getSubject().getId());
        return lessonParser.toLessonDetailsDTO(lesson, homeworkResponseDto, resources);
    }
}
