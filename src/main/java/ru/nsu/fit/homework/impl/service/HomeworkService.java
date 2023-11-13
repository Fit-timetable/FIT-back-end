package ru.nsu.fit.homework.impl.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.homework.api.dto.HomeworkResponseDto;
import ru.nsu.fit.homework.impl.data.GroupHomeworkRepository;
import ru.nsu.fit.homework.impl.data.HomeworkFileRepository;
import ru.nsu.fit.homework.impl.data.HomeworkRepository;
import ru.nsu.fit.homework.impl.domain.model.Homework;
import ru.nsu.fit.homework.impl.domain.model.HomeworkFile;
import ru.nsu.fit.homework.impl.domain.service.HomeworkParser;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class HomeworkService {
    private HomeworkRepository homeworkRepository;
    private HomeworkFileRepository homeworkFileRepository;
    private GroupHomeworkRepository groupHomeworkRepository;
    private HomeworkParser homeworkParser;

    public Homework getNearestHomeworkForLesson(Long id) {
        List<Homework> homeworks = homeworkRepository.findAllByLessonId(id);
        Instant now = Instant.now();
        Homework closestHomework = null;
        Duration minDuration = Duration.between(now, homeworks.get(0).getDeadline());
        for (Homework homework : homeworks) {
            Duration duration = Duration.between(now, homework.getDeadline());
            if (duration.compareTo(minDuration) < 0) {
                minDuration = duration;
                closestHomework = homework;
            }
        }
        return closestHomework;
    }

    public HomeworkFile getFileForHomework(Long id) {
        return homeworkFileRepository.findHomeworkFileByHomeworkId(id);
    }

    public Boolean isHomeworkShared(Long id) {
        return groupHomeworkRepository.findByHomeworkId(id) != null;
    }

    public HomeworkResponseDto getHomeworkResponseDtoByLessonId(Long id){
        Homework homework = getNearestHomeworkForLesson(id);
        HomeworkFile homeworkFile = getFileForHomework(homework.getId());
        Boolean isShared = isHomeworkShared(homework.getId());
        return homeworkParser.toHomeworkResponseDto(homework, homeworkFile, isShared);
    }
}
