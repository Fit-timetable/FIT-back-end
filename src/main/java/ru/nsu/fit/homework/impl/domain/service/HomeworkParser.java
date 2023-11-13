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
public class HomeworkParser {
    public HomeworkResponseDto toHomeworkResponseDto(Homework homework, HomeworkFile homeworkFile, Boolean isShared){
        //крч text и notificationPriod должны хранится в бд
        //надо добавить эти атрибуты в таблицу
        //ну и etity изменить, я пока заглушки поставил
        //и еще с последним параметром какую то чушь сделал, можно было умнее но времени нет сори
        String homeworkText = "adsf";
        Integer notificationPeriod = 0;
        return new HomeworkResponseDto(
                homework.getId(),
                homework.getStudentId(),
                homework.getDeadline().toString(),
                homeworkText,
                Integer.parseInt(homework.getEstimatedTime()),
                homeworkFile.getFileDriveUri(),
                isShared,
                notificationPeriod,
                Integer.parseInt(String.valueOf(homework.getDaysBeforeDeadlineReminder()))
                );
    }
}
