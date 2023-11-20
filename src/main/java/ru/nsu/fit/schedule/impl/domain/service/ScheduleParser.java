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

    private static String checkNullable(Element element) {
        return (element != null) ? element.text() : "";
    }

    private static String getElementText(Elements elements, int k) {
        return checkNullable(elements.size() > k ? elements.get(k) : null);
    }

    private static LessonParity getLessonParity(String lessonParity) {
        return switch (lessonParity) {
            case "Четная" -> LessonParity.EVEN;
            case "Нечетная" -> LessonParity.ODD;
            case "" -> LessonParity.ALWAYS;
            default -> null;
        };
    }

    private static LessonType getLessonType(String lessonType) {
        return switch (lessonType) {
            case "лек" -> LessonType.LECTURE;
            case "пр" -> LessonType.SEMINAR;
            case "лаб" -> LessonType.LABORATORY;
            default -> null;
        };
    }

    private static String getTime(Element timeElement) {
        return timeElement != null ? timeElement.text() : null;
    }

    public static WeekScheduleDto parseByGroup(String group) {
        return parseSchedule(null, ScheduleUrl.NSU_GROUP_URL + group);
    }

    public static WeekScheduleDto parseByRoom(String room) {
        return parseSchedule(room, ScheduleUrl.NSU_ROOM_URL + room);
    }

    private static void storeOpeningLessonScheduleDtoTemporarily(
            Element currentCell,
            List<LessonScheduleDto> openingLessons,
            String startTime,
            String finishTime
    ) {
        if (currentCell.select(".subject").isEmpty()) {
            LessonScheduleDto lessonScheduleDto = new LessonScheduleDto(
                    null,
                    null,
                    LessonType.OPENING,
                    startTime,
                    finishTime,
                    null,
                    null,
                    null);
            openingLessons.add(lessonScheduleDto);
        }
    }

    private static LessonScheduleDto createLessonScheduleDto(
            Element currentCell,
            String startTime,
            String finishTime,
            String room,
            int k
    ) {
        String subject = getElementText(currentCell.select(".subject"), k);
        LessonType type = getLessonType(getElementText(currentCell.select(".type"), k));
        String teacher = getElementText(currentCell.select(".tutor"), k);
        LessonPlace place = (room != null) ? new LessonPlace(room, null) :
                new LessonPlace(getElementText(currentCell.select(".room"), k), null);
        LessonParity parity = getLessonParity(getElementText(currentCell.select(".week"), k));

        return new LessonScheduleDto(null, subject, type, startTime, finishTime, teacher, place, parity);
    }

    private static WeekScheduleDto parseSchedule(String room, String url) {
        List<DayScheduleDto> days = new ArrayList<>();
        try {
            Document document = Jsoup.connect(url).get();

            Elements scheduleElements = document.select("table.time-table");

            for (int i = 0; i < DAYS_IN_WEEK; i++) {
                List<LessonScheduleDto> lessons = new ArrayList<>();
                List<LessonScheduleDto> openingLessons = new ArrayList<>();
                for (Element scheduleElement : scheduleElements) {
                    Elements rows = scheduleElement.select("tr");
                    for (Element row : rows) {
                        Elements cells = row.select("td");

                        if (cells.isEmpty()) {
                            continue;
                        }

                        String startTime = getTime(cells.first());
                        assert startTime != null;
                        String finishTime = DomainScheduleService.getFinishTime(startTime);

                        Element currentCell = cells.get(i + 1);

                        storeOpeningLessonScheduleDtoTemporarily(currentCell, openingLessons, startTime, finishTime);

                        for (int k = 0; k < currentCell.select(".subject").size(); k++) {
                            if (k == 0) {
                                lessons.addAll(openingLessons);
                                openingLessons = new ArrayList<>();
                            }
                            lessons.add(createLessonScheduleDto(currentCell, startTime, finishTime, room, k));
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
