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

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static ru.nsu.fit.schedule.impl.domain.service.DomainScheduleService.CORRECT_DATE_FORMAT_LEN;

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
        return parseSchedule(null, ScheduleUrl.NSU_GROUP_URL + group);
    }

    public static WeekScheduleDto parseByRoom(String room) {
        return parseSchedule(room, ScheduleUrl.NSU_ROOM_URL + room);
    }

    public static String getFinishTime(String startTime) {
        String formattedStartTime = (startTime.length() == CORRECT_DATE_FORMAT_LEN - 1) ? "0" + startTime : startTime;
        LocalTime start = LocalTime.parse(formattedStartTime);
        LocalTime finish = start.plusHours(1).plusMinutes(35);
        return finish.toString();
    }

    private static WeekScheduleDto parseSchedule(String room, String url) {
        List<DayScheduleDto> days = new ArrayList<>();
        try {
            ScheduleParser scheduleParser = new ScheduleParser();

            Document document = Jsoup.connect(url).get();

            Elements scheduleElements = document.select("table.time-table");

            for (int i = 0; i < DAYS_IN_WEEK; i++) {
                List<LessonScheduleDto> lessons = new ArrayList<>();
                List<LessonScheduleDto> emptyLessons = new ArrayList<>();
                for (Element scheduleElement : scheduleElements) {
                    Elements rows = scheduleElement.select("tr");
                    for (Element row : rows) {
                        Elements cells = row.select("td");

                        if (cells.isEmpty()) {
                            continue;
                        }

                        Element currentCell = cells.get(i + 1);

                        String startTime = scheduleParser.getTime(cells.first());
                        assert startTime != null;
                        String finishTime = getFinishTime(startTime);

                        if (currentCell.select(".subject").size() == 0) {
                            LessonScheduleDto lessonScheduleDto = new LessonScheduleDto(
                                    null,
                                    null,
                                    LessonType.OPENING,
                                    startTime,
                                    finishTime ,
                                    null,
                                    null,
                                    null);
                            emptyLessons.add(lessonScheduleDto);
                        }

                        for (int k = 0; k < currentCell.select(".subject").size(); k++) {
                            if (!emptyLessons.isEmpty()) {
                                lessons.addAll(emptyLessons);
                                emptyLessons = new ArrayList<>();
                            }

                            String subject = scheduleParser.getElementText(currentCell.select(".subject"), k);
                            LessonType type = scheduleParser.getLessonType(scheduleParser.getElementText(currentCell.select(".type"), k));
                            String teacher = scheduleParser.getElementText(currentCell.select(".tutor"), k);
                            LessonPlace place = (room != null) ? new LessonPlace(room, null) :
                                    new LessonPlace(scheduleParser.getElementText(currentCell.select(".room"), k), null);
                            LessonParity parity = scheduleParser.getLessonParity(scheduleParser.getElementText(currentCell.select(".week"), k));

                            LessonScheduleDto lessonScheduleDto = new LessonScheduleDto(null, subject, type, startTime, finishTime, teacher, place, parity);
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
