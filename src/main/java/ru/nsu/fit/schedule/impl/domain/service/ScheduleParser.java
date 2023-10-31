package ru.nsu.fit.schedule.impl.domain.service;

import lombok.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import ru.nsu.fit.schedule.impl.data.entities.*;
import ru.nsu.fit.schedule.impl.data.repositories.*;
import ru.nsu.fit.schedule.impl.domain.model.Lesson;
import ru.nsu.fit.schedule.port.ScheduleUrl;

import java.util.*;

@Component
@Getter
@AllArgsConstructor
public class ScheduleParser {
    private final List<Lesson>[][] lessons = new ArrayList[DAYS_IN_WEEK][LESSONS_IN_DAY];

    private GroupRepository groupRepository;
    private StudentRepository studentRepository;
    private GroupStudentRepository groupStudentRepository;
    private GlobalModeratorRepository globalModeratorRepository;
    private SubjectRepository subjectRepository;
    private StudentLessonRepository studentLessonRepository;
    private LessonRepository lessonRepository;

    private static final int DAYS_IN_WEEK = 6;
    private static final int LESSONS_IN_DAY = 7;
    private static final int GROUP_NUMBER = 20209;


    private String checkNullable(Element element) {
        return (element != null) ? element.text() : "";
    }

    private String getElementText(Elements elements, int k) {
        return checkNullable(elements.size() > k ? elements.get(k) : null);
    }

    public void pinSchedule(Long studentId, String group) {
        GroupEntity groupEntity = groupRepository.findByNumber(group);
        if (groupEntity == null) {
            throw new IllegalArgumentException("Group not found");
        }

        StudentEntity studentEntity = studentRepository.findById(studentId).orElse(null);
        if (studentEntity == null) {
            throw new IllegalArgumentException("Student not found");
        }

        if (groupStudentRepository.findByGroupEntityAndStudentEntity(groupEntity, studentEntity) == null) {
            GlobalModeratorEntity globalModeratorEntity = globalModeratorRepository
                    .findGlobalModeratorByGlobalModeratorEntityKey_StudentEntityId(studentId);

            GroupStudentEntity groupStudentEntity = new GroupStudentEntity();
            groupStudentEntity.setGroupEntity(groupEntity);
            groupStudentEntity.setStudentEntity(studentEntity);
            groupStudentEntity.setModerator(globalModeratorEntity != null);

            groupStudentRepository.save(groupStudentEntity);
        }

        List<LessonEntity> lessonEntities = lessonRepository.findByGroupEntity_Id(groupEntity.getId());

        for (LessonEntity lessonEntity : lessonEntities) {
            if (studentLessonRepository
                    .findByStudentEntityAndLessonEntity(studentEntity, lessonEntity) == null) {
                StudentLessonEntity studentLessonEntity = new StudentLessonEntity();
                studentLessonEntity.setStudentEntity(studentEntity);
                studentLessonEntity.setLessonEntity(lessonEntity);
                studentLessonEntity.setVisited(true);

                studentLessonRepository.save(studentLessonEntity);
            }
        }
    }

    public void resetSchedule(Long studentId) {
        List<StudentLessonEntity> studentLessonEntities = studentLessonRepository.findByStudentEntity_Id(studentId);
        studentLessonRepository.deleteAll(studentLessonEntities);

        StudentEntity studentEntity = studentRepository.findById(studentId).orElse(null);
        if (studentEntity == null) {
            throw new IllegalArgumentException("Student not found");
        }

        GroupStudentEntity groupStudentEntity = groupStudentRepository.findByStudentEntity(studentEntity);
        groupStudentRepository.delete(groupStudentEntity);
    }

    private void parse() {
        try {
            Document document = Jsoup.connect(ScheduleUrl.GROUP_URL + GROUP_NUMBER).get();

            Elements scheduleElements = document.select("table.time-table");
            int j;

            for (int i = 0; i < DAYS_IN_WEEK; i++) {
                j = 0;
                for (Element scheduleElement : scheduleElements) {
                    Elements rows = scheduleElement.select("tr");
                    for (Element row : rows) {
                        Elements cells = row.select("td");

                        if (!cells.isEmpty()) {
                            Element currentCell = cells.get(i + 1);

                            lessons[i][j] = new ArrayList<>();
                            for (int k = 0; k < currentCell.select(".subject").size(); k++) {
                                String subject = getElementText(currentCell.select(".subject"), k);
                                String tutor = getElementText(currentCell.select(".tutor"), k);
                                String room = getElementText(currentCell.select(".room"), k);
                                String type = getElementText(currentCell.select(".type"), k);
                                String week = getElementText(currentCell.select(".week"), k);

                                lessons[i][j].add(new Lesson(subject, tutor, room, type, week));
                            }
                            j++;
                        }
                    }
                }
            }
            printTimetable();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printTimetable() {
        System.out.println("/******************************************/");
        for (int i = 0; i < DAYS_IN_WEEK; i++) {
            for (int j = 0; j < LESSONS_IN_DAY; j++) {
                System.out.print(j + 1 + ". ");
                for (int k = 0; k < lessons[i][j].size(); k++) {
                    if (k > 0) {
                        System.out.print(" / ");
                    }
                    System.out.print(
                            lessons[i][j].get(k).subject() + " " +
                            lessons[i][j].get(k).type() + " " +
                            lessons[i][j].get(k).tutor() + " " +
                            lessons[i][j].get(k).room() + " " +
                            lessons[i][j].get(k).week());
                }
                System.out.print("\n");
            }
            System.out.println("/******************************************/");
        }
    }
}
