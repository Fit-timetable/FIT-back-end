package ru.nsu.fit.homework.impl.domain.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.nsu.fit.homework.api.dto.HomeworkResponseDto;
import ru.nsu.fit.homework.impl.domain.model.Homework;
import ru.nsu.fit.homework.impl.domain.model.HomeworkFile;

import java.util.Optional;

@Component
@Getter
@AllArgsConstructor
public class HomeworkParser {
    public HomeworkResponseDto toHomeworkResponseDto(Homework homework, Optional<HomeworkFile> homeworkFile, Boolean isShared){
        Integer notificationPeriodSeconds = null;
        if (homework.getNotificationPeriod() != null){
            notificationPeriodSeconds = (int) homework.getNotificationPeriod().toSeconds();
        }
        return new HomeworkResponseDto(
                homework.getId(),
                homework.getStudentId(),
                homework.getDeadline().toString(),
                homework.getHomeworkText(),
                (int) homework.getEstimatedTime().toSeconds(),
                homeworkFile.map(HomeworkFile::getFileDriveUri).orElse(null),
                isShared,
                notificationPeriodSeconds,
                homework.getDaysBeforeDeadlineReminder()
                );
    }

}
