package ru.nsu.fit.lesson.impl.domain.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.nsu.fit.homework.api.dto.HomeworkResponseDto;
import ru.nsu.fit.lesson.api.dto.LessonDetailsDto;
import ru.nsu.fit.lesson.impl.domain.model.LessonPlace;
import ru.nsu.fit.lesson.impl.domain.model.entities.Lesson;
import ru.nsu.fit.resource.api.dto.ResourceResponseDto;

import java.util.List;

@Component
@Getter
@AllArgsConstructor
public class LessonParser {
    public LessonDetailsDto toLessonDetailsDTO(Lesson lesson, HomeworkResponseDto homeworkResponseDto, List<ResourceResponseDto> resources){
        LessonPlace lessonPlace = new LessonPlace(lesson.getRoom(), lesson.getMeetLink());
        return new LessonDetailsDto(lesson.getId(),
                lesson.getStartTime().toString(),
                lesson.getSubject().getName(),
                lessonPlace,
                lesson.getTeacher(),
                homeworkResponseDto,
                resources);
    }

}
