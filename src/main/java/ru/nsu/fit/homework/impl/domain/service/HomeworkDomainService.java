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
public class HomeworkDomainService {
    public HomeworkResponseDto toHomeworkResponseDto(Homework homework, HomeworkFile homeworkFile, Boolean isShared){
        String homeworkText = "";
        Integer notificationPeriod = 0;

        return new HomeworkResponseDto(
                homework.getId(),
                homework.getStudentId(),
                homework.getDeadline().toString(),
                homeworkText,
                (int) homework.getEstimatedTime().getSeconds(),
                homeworkFile.getFileDriveUri(),
                isShared,
                notificationPeriod,
                homework.getDaysBeforeDeadlineReminder()
                );
    }

}
