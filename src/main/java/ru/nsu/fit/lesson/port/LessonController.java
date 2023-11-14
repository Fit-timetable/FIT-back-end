package ru.nsu.fit.lesson.port;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.lesson.api.LessonForm;
import ru.nsu.fit.lesson.api.LessonService;
import ru.nsu.fit.lesson.impl.domain.model.entities.Lesson;

import java.time.LocalDate;

import static ru.nsu.fit.lesson.port.LessonUrl.*;

@RestController
@AllArgsConstructor
@RequestMapping(LESSON_URL)
public class LessonController {

    private final LessonService lessonService;

    @PostMapping()
    public Long createLesson(@RequestBody LessonForm lesson) {
        return lessonService.createLesson(lesson).getId();
    }

    @PostMapping(LESSON_CANCEL_URL)
    public Lesson cancelLesson(@PathVariable Long id, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
        return lessonService.cancelLesson(id, localDate);
    }

    @PutMapping(LESSON_ID_URL)
    public Lesson editLesson(@PathVariable Long id, @RequestBody Lesson lesson) {
        return lessonService.editLesson(id, lesson);
    }

    @DeleteMapping(LESSON_ID_URL)
    public Lesson deleteLesson(@PathVariable Long id) {
        return lessonService.deleteLesson(id);
    }
}
