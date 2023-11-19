package ru.nsu.fit.lesson.port;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.lesson.api.LessonForm;
import ru.nsu.fit.lesson.api.LessonService;
import ru.nsu.fit.lesson.api.dto.CancelLessonDto;
import ru.nsu.fit.lesson.api.dto.EditLessonDto;
import ru.nsu.fit.lesson.api.dto.LessonIdDto;

import static ru.nsu.fit.lesson.port.LessonUrl.*;

@RestController
@AllArgsConstructor
@Tag(name="Занятие")
@RequestMapping(LESSON_URL)
public class LessonController {

    private final LessonService lessonService;

    @Operation(summary = "Добавить новое занятие")
    @PostMapping()
    public LessonIdDto createLesson(@RequestBody LessonForm lesson) {
        return new LessonIdDto(lessonService.createLesson(lesson).getId());
    }

    @Operation(summary = "Отменить занятие на определенную дату")
    @PostMapping(LESSON_CANCEL_URL)
    public void cancelLesson(@PathVariable Long id, @RequestBody CancelLessonDto lesson) {
        lessonService.cancelLesson(id, lesson.date());
    }

    @Operation(summary = "Редактировать занятие")
    @PutMapping(LESSON_ID_URL)
    public void editLesson(@PathVariable Long id, @RequestBody EditLessonDto lesson) {
        lessonService.editLesson(id, lesson);
    }

    @Operation(summary = "Удалить занятие")
    @DeleteMapping(LESSON_ID_URL)
    public void deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
    }
}
