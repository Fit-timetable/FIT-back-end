package ru.nsu.fit.lesson.port;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.lesson.api.LessonForm;
import ru.nsu.fit.lesson.api.LessonService;

import static ru.nsu.fit.lesson.port.LessonUrl.ID_URL;
import static ru.nsu.fit.lesson.port.LessonUrl.LESSON_URL;

@RestController
@AllArgsConstructor
@RequestMapping(LESSON_URL)
public class LessonController {

    private final LessonService lessonService;

    @PostMapping()
    public Long createLesson(@RequestBody LessonForm lesson) {
        return lessonService.createLesson(lesson).getId();
    }

    @GetMapping(ID_URL)
    public String getLesson(@PathVariable Long id){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(lessonService.getLesson(id));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
