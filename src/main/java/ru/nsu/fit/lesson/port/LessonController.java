package ru.nsu.fit.lesson.port;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import ru.nsu.fit.lesson.api.LessonForm;
import ru.nsu.fit.lesson.api.LessonService;

import static ru.nsu.fit.lesson.port.LessonUrl.*;

@RestController
@AllArgsConstructor
@RequestMapping(LESSON_URL)
public class LessonController {
    private LessonService lessonService;

    @PostMapping()
    public Long createLesson(@RequestBody LessonForm lesson) {
        return lessonService.createLesson(lesson).getId();
    }
}
