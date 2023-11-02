package ru.nsu.fit.schedule.impl.domain.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.nsu.fit.schedule.api.dto.DayScheduleDto;
import ru.nsu.fit.schedule.api.dto.LessonScheduleDto;
import ru.nsu.fit.schedule.api.dto.WeekScheduleDto;
import ru.nsu.fit.schedule.impl.domain.model.LessonParity;
import ru.nsu.fit.schedule.impl.domain.model.LessonPlace;
import ru.nsu.fit.schedule.impl.domain.model.LessonType;

import java.util.*;

@RestController
@RequestMapping("/schedule")
public class ScheduleParser {
    private String checkNullable(Element element) {
        return (element != null) ? element.text() : "";
    }

    private String getElementText(Elements elements, int k) {
        return checkNullable(elements.size() > k ? elements.get(k) : null);
    }

    private LessonParity getLessonParity(String lessonParity){
        if(lessonParity.equals("Четная")){
            return LessonParity.EVEN;
        }

        if(lessonParity.equals("Нечетная")){
            return LessonParity.ODD;
        }

        if(lessonParity.equals("")){
            return LessonParity.ALWAYS;
        }

        return null;
    }

    private LessonType getLessonType(String lessonType){
        if(lessonType.equals("лек")){
            return LessonType.LECTURE;
        }

        if(lessonType.equals("пр")){
            return LessonType.SEMINAR;
        }        


        if(lessonType.equals("лаб")){
            return LessonType.LABORATORY;
        }

        return null;
    }

    private String getTime(Element timeElement){
         if(timeElement != null){
            return timeElement.text();
        }

        return null;
    }

    @GetMapping("/group/{group}")
    public static WeekScheduleDto parseByGroup(@PathVariable String group) {
        List<DayScheduleDto> days = new ArrayList<>(); 
        List<LessonScheduleDto> lessons = new ArrayList<>();
        
        int DAYS_IN_WEEK = 6;

        try {
            ScheduleParser scheduleParser = new ScheduleParser();

            Document document = Jsoup.connect("https://table.nsu.ru/group/" + group).get();

            Elements scheduleElements = document.select("table.time-table");

            for (int i = 0; i < DAYS_IN_WEEK; i++) {
                for (Element scheduleElement : scheduleElements) {
                    Elements rows = scheduleElement.select("tr");
                    for (Element row : rows) {
                        Elements cells = row.select("td");

                        if (!cells.isEmpty()) {
                            Element currentCell = cells.get(i + 1);
                   
                            LessonScheduleDto lessonScheduleDto;

                            for (int k = 0; k < currentCell.select(".subject").size(); k++) {
                                String subject = scheduleParser.getElementText(currentCell.select(".subject"), k);
                                LessonType type = scheduleParser.getLessonType(scheduleParser.getElementText(currentCell.select(".type"), k));
                                String startTime = scheduleParser.getTime(cells.first());
                                String teacher = scheduleParser.getElementText(currentCell.select(".tutor"), k);
                                LessonPlace place = new LessonPlace(scheduleParser.getElementText(currentCell.select(".room"), k), null);
                                LessonParity parity = scheduleParser.getLessonParity(scheduleParser.getElementText(currentCell.select(".week"), k));
                                lessonScheduleDto = new LessonScheduleDto(subject, type, startTime, teacher, place, parity);
                                lessons.add(lessonScheduleDto);
                            }
                        }
                    }
                }

                days.add(new DayScheduleDto(lessons));
                lessons = new ArrayList<>();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        WeekScheduleDto weekScheduleDto = new WeekScheduleDto(days.get(0), days.get(1), 
        days.get(2), days.get(3), days.get(4), days.get(5));

        return weekScheduleDto;
    }

    @GetMapping("/room/{room}")
    public static WeekScheduleDto parseByRoom(@PathVariable String room) {
        List<DayScheduleDto> days = new ArrayList<>(); 
        List<LessonScheduleDto> lessons = new ArrayList<>();
        
        int DAYS_IN_WEEK = 6;

        try {
            ScheduleParser scheduleParser = new ScheduleParser();

            Document document = Jsoup.connect("https://table.nsu.ru/room/" + room).get();

            Elements scheduleElements = document.select("table.time-table");

            for (int i = 0; i < DAYS_IN_WEEK; i++) {
                for (Element scheduleElement : scheduleElements) {
                    Elements rows = scheduleElement.select("tr");
                    for (Element row : rows) {
                        Elements cells = row.select("td");

                        if (!cells.isEmpty()) {
                            Element currentCell = cells.get(i + 1);
                   
                            LessonScheduleDto lessonScheduleDto;

                            for (int k = 0; k < currentCell.select(".subject").size(); k++) {
                                String subject = scheduleParser.getElementText(currentCell.select(".subject"), k);
                                LessonType type = scheduleParser.getLessonType(scheduleParser.getElementText(currentCell.select(".type"), k));
                                String startTime = scheduleParser.getTime(cells.first());
                                String teacher = scheduleParser.getElementText(currentCell.select(".tutor"), k);
                                LessonPlace place = new LessonPlace(room, null);
                                LessonParity parity = scheduleParser.getLessonParity(scheduleParser.getElementText(currentCell.select(".week"), k));
                                lessonScheduleDto = new LessonScheduleDto(subject, type, startTime, teacher, place, parity);
                                lessons.add(lessonScheduleDto);
                            }
                        }
                    }
                }

                days.add(new DayScheduleDto(lessons));
                lessons = new ArrayList<>();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        WeekScheduleDto weekScheduleDto = new WeekScheduleDto(days.get(0), days.get(1), 
        days.get(2), days.get(3), days.get(4), days.get(5));

        return weekScheduleDto;
    }
}
