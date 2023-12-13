package ru.nsu.fit.homework.impl.domain.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.nsu.fit.homework.api.dto.HomeworkResponseDto;
import ru.nsu.fit.homework.impl.domain.model.Homework;
import ru.nsu.fit.homework.impl.domain.model.HomeworkFile;

@Component
@Getter
@AllArgsConstructor
public class HomeworkDomainService {
    public HomeworkResponseDto toHomeworkResponseDto(Homework homework, HomeworkFile homeworkFile, Boolean isShared){
        return new HomeworkResponseDto(
                homework.getId(),
                homework.getStudentId(),
                homework.getDeadline().toString(),
                homework.getHomeworkText(),
                (int) homework.getEstimatedTime().getSeconds(),
                homeworkFile.getFileDriveUri(),
                isShared,
                (int)homework.getNotificationPeriod().getSeconds(),
                homework.getDaysBeforeDeadlineReminder()
                );
    }

}
