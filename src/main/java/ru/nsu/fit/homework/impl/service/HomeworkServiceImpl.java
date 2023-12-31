package ru.nsu.fit.homework.impl.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import ru.nsu.fit.homework.api.HomeworkService;
import ru.nsu.fit.homework.api.dto.HomeworkResponseDto;
import ru.nsu.fit.homework.impl.data.GroupHomeworkRepository;
import ru.nsu.fit.homework.impl.data.HomeworkFileRepository;
import ru.nsu.fit.homework.impl.data.HomeworkRepository;
import ru.nsu.fit.homework.impl.domain.model.Homework;
import ru.nsu.fit.homework.impl.domain.model.HomeworkFile;
import ru.nsu.fit.homework.impl.domain.service.HomeworkDomainService;

import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class HomeworkServiceImpl implements HomeworkService{
    private HomeworkRepository homeworkRepository;
    private HomeworkFileRepository homeworkFileRepository;
    private GroupHomeworkRepository groupHomeworkRepository;
    private HomeworkDomainService homeworkDomainService;

    private Homework getNearestHomeworkForLesson(Long id) {
        List<Homework> homeworks = homeworkRepository.findAllByLessonId(id);
        
        if(homeworks.isEmpty()){
            throw new NoSuchElementException("Homeworks doesn't exist");
        }

        Instant now = Instant.now();
        Homework closestHomework = null;
        Duration minDuration = Duration.between(now, homeworks.get(0).getDeadline());
        for (Homework homework : homeworks) {
            Duration duration = Duration.between(now, homework.getDeadline());
            if (duration.compareTo(minDuration) <= 0) {
                minDuration = duration;
                closestHomework = homework;
            }
        }
        return closestHomework;
    }

    private HomeworkFile getFileForHomework(Long id) {
        return homeworkFileRepository.findHomeworkFileByHomeworkId(id).orElse(null);
    }

    private Boolean isHomeworkShared(Long id) {
        return groupHomeworkRepository.findByHomeworkId(id).isPresent();
    }

    @Override
    public HomeworkResponseDto getNearestHomeworkResponseDtoByLessonId(Long id){
        Homework homework = getNearestHomeworkForLesson(id);

        if(homework == null){
            throw new NoSuchElementException("Homework doesn't exist");
        }

        HomeworkFile homeworkFile = getFileForHomework(homework.getId());
        Boolean isShared = isHomeworkShared(homework.getId());

        return homeworkDomainService.toHomeworkResponseDto(homework, homeworkFile, isShared);
    }

    @Override
    public void saveHomework(Long studentId, Long lessonId, ZonedDateTime deadline, Short daysBeforeDeadlineReminder,
                         Duration estimatedTime, String homeworkText, Duration notificationPeriod) {

        homeworkRepository.save(new Homework(studentId, lessonId, deadline, daysBeforeDeadlineReminder,
                                     estimatedTime, homeworkText, notificationPeriod));
    }
}
