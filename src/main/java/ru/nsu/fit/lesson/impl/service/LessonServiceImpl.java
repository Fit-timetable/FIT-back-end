package ru.nsu.fit.lesson.impl.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import ru.nsu.fit.homework.api.HomeworkService;
import ru.nsu.fit.homework.api.dto.HomeworkResponseDto;
import ru.nsu.fit.lesson.api.LessonForm;
import ru.nsu.fit.lesson.api.LessonService;
import ru.nsu.fit.lesson.api.dto.EditLessonDto;
import ru.nsu.fit.lesson.api.dto.LessonDetailsDto;
import ru.nsu.fit.lesson.impl.data.CanceledLessonRepository;
import ru.nsu.fit.lesson.impl.data.LessonRepository;
import ru.nsu.fit.lesson.impl.domain.model.entities.Lesson;
import ru.nsu.fit.lesson.impl.domain.service.DomainLessonService;
import ru.nsu.fit.resource.api.dto.ResourceResponseDto;
import ru.nsu.fit.resource.api.ResouceService;
import ru.nsu.fit.student.api.StudentService;
import ru.nsu.fit.student.impl.data.StudentLessonRepository;
import ru.nsu.fit.student.impl.domain.model.StudentLesson;
import ru.nsu.fit.subject.api.SubjectService;
import ru.nsu.fit.subject.impl.domain.model.Subject;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@AllArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final StudentLessonRepository studentLessonRepository;
    private final CanceledLessonRepository canceledLessonRepository;
    private final SubjectService subjectService;
    private final StudentService studentService;
    private HomeworkService homeworkService;
    private ResouceService resourceService;

    @Override
    public Lesson createLesson(LessonForm lessonForm) {
        Subject subject = subjectService.getSubject(lessonForm.subjectId());
        Lesson lesson = lessonRepository.save(DomainLessonService.mapping(
                lessonForm.date().startTime(),
                lessonForm.date().weekDay(),
                lessonForm.place().room(),
                lessonForm.place().meetLink(),
                lessonForm.type(),
                lessonForm.parity(),
                subject)
        );
        var student = studentService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.UNAUTHORIZED));

        studentService.saveStudentLesson(DomainLessonService.mapping(lesson, studentService.getStudent(student.getId())));

        return lesson;
    }

    @Override
    public void cancelLesson(Long id, Date date) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow();
        List<StudentLesson> studentLessons = studentLessonRepository.findByLessonId(id);

        for (StudentLesson studentLesson : studentLessons) {
            canceledLessonRepository.save(DomainLessonService.mapping(lesson, date, studentLesson));
        }
    }

    @Override
    public void editLesson(Long id, EditLessonDto editLessonDto) {
        Subject subject = subjectService.getSubject(editLessonDto.subjectId());
        Lesson existingLesson = lessonRepository.findById(id).orElseThrow();

        lessonRepository.save(DomainLessonService.mapping(
                editLessonDto.date().startTime(),
                editLessonDto.date().weekDay(),
                editLessonDto.teacher(),
                editLessonDto.place().room(),
                editLessonDto.place().meetLink(),
                existingLesson,
                subject)
        );
    }

    @Override
    public void deleteLesson(Long id) {
        lessonRepository.deleteById(id);
    }

    @Override
    public LessonDetailsDto getLessonDetails(Long id) {
        Lesson lesson = lessonRepository.findById(id).orElse(null);

        if(lesson == null){
            throw new NoSuchElementException("Lesson doesn't exist");
        }

        HomeworkResponseDto homeworkResponseDto = homeworkService.getNearestHomeworkResponseDtoByLessonId(id);
        List<ResourceResponseDto> resources = resourceService.getResourcesDtoBySubjectId(lesson.getSubject().getId());
        
        return DomainLessonService.toLessonDetailsDTO(lesson, homeworkResponseDto, resources);
    }
}
