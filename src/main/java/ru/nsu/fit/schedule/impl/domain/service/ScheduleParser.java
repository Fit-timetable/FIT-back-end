package ru.nsu.fit.schedule.impl.domain.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import ru.nsu.fit.lesson.impl.domain.model.LessonParity;
import ru.nsu.fit.lesson.impl.domain.model.LessonPlace;
import ru.nsu.fit.lesson.impl.domain.model.LessonType;
import ru.nsu.fit.schedule.api.dto.DayScheduleDto;
import ru.nsu.fit.schedule.api.dto.LessonScheduleDto;
import ru.nsu.fit.schedule.api.dto.WeekScheduleDto;
import ru.nsu.fit.schedule.port.ScheduleUrl;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@AllArgsConstructor
public class ScheduleParser {
    private static final int DAYS_IN_WEEK = 6;

    private String checkNullable(Element element) {
        return (element != null) ? element.text() : "";
    }

    private String getElementText(Elements elements, int k) {
        return checkNullable(elements.size() > k ? elements.get(k) : null);
    }

    private LessonParity getLessonParity(String lessonParity) {
        return switch (lessonParity) {
            case "Четная" -> LessonParity.EVEN;
            case "Нечетная" -> LessonParity.ODD;
            case "" -> LessonParity.ALWAYS;
            default -> null;
        };
    }

    private LessonType getLessonType(String lessonType) {
        return switch (lessonType) {
            case "лек" -> LessonType.LECTURE;
            case "пр" -> LessonType.SEMINAR;
            case "лаб" -> LessonType.LABORATORY;
            default -> null;
        };
    }

    private String getTime(Element timeElement) {
        return timeElement != null ? timeElement.text() : null;
    }

    public static WeekScheduleDto parseByGroup(String group) {
        List<DayScheduleDto> days = new ArrayList<>();
        try {
            ScheduleParser scheduleParser = new ScheduleParser();

            Document document = Jsoup.connect(ScheduleUrl.NSU_GROUP_URL + group).get();

            Elements scheduleElements = document.select("table.time-table");

            for (int i = 0; i < DAYS_IN_WEEK; i++) {
                List<LessonScheduleDto> lessons = new ArrayList<>();
                for (Element scheduleElement : scheduleElements) {
                    Elements rows = scheduleElement.select("tr");
                    for (Element row : rows) {
                        Elements cells = row.select("td");

                        if (cells.isEmpty()) {
                            continue;
                        }

                        Element currentCell = cells.get(i + 1);

                        for (int k = 0; k < currentCell.select(".subject").size(); k++) {
                            String subject = scheduleParser.getElementText(currentCell.select(".subject"), k);
                            LessonType type = scheduleParser.getLessonType(scheduleParser.getElementText(currentCell.select(".type"), k));
                            String startTime = scheduleParser.getTime(cells.first());
                            String teacher = scheduleParser.getElementText(currentCell.select(".tutor"), k);
                            LessonPlace place = new LessonPlace(scheduleParser.getElementText(currentCell.select(".room"), k), null);
                            LessonParity parity = scheduleParser.getLessonParity(scheduleParser.getElementText(currentCell.select(".week"), k));

                            LessonScheduleDto lessonScheduleDto = new LessonScheduleDto(null, subject, type, startTime, teacher, place, parity);
                            lessons.add(lessonScheduleDto);
                        }
                    }
                }
                days.add(new DayScheduleDto(lessons));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new WeekScheduleDto(
                days.get(0),
                days.get(1),
                days.get(2),
                days.get(3),
                days.get(4),
                days.get(5)
        );
    }

    public static WeekScheduleDto parseByRoom(String room) {
        List<DayScheduleDto> days = new ArrayList<>();
        try {
            ScheduleParser scheduleParser = new ScheduleParser();

            Document document = Jsoup.connect(ScheduleUrl.NSU_ROOM_URL + room).get();

            Elements scheduleElements = document.select("table.time-table");

            for (int i = 0; i < DAYS_IN_WEEK; i++) {
                List<LessonScheduleDto> lessons = new ArrayList<>();
                for (Element scheduleElement : scheduleElements) {
                    Elements rows = scheduleElement.select("tr");
                    for (Element row : rows) {
                        Elements cells = row.select("td");

                        if (cells.isEmpty()) {
                            continue;
                        }

                        Element currentCell = cells.get(i + 1);

                        for (int k = 0; k < currentCell.select(".subject").size(); k++) {
                            String subject = scheduleParser.getElementText(currentCell.select(".subject"), k);
                            LessonType type = scheduleParser.getLessonType(scheduleParser.getElementText(currentCell.select(".type"), k));
                            String startTime = scheduleParser.getTime(cells.first());
                            String teacher = scheduleParser.getElementText(currentCell.select(".tutor"), k);
                            LessonPlace place = new LessonPlace(room, null);
                            LessonParity parity = scheduleParser.getLessonParity(scheduleParser.getElementText(currentCell.select(".week"), k));

                            LessonScheduleDto lessonScheduleDto = new LessonScheduleDto(null, subject, type, startTime, teacher, place, parity);
                            lessons.add(lessonScheduleDto);
                        }

                    }
                }
                days.add(new DayScheduleDto(lessons));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new WeekScheduleDto(
                days.get(0),
                days.get(1),
                days.get(2),
                days.get(3),
                days.get(4),
                days.get(5)
        );
    }
}
