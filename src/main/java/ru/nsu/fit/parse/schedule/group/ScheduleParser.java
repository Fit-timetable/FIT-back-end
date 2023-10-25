package ru.nsu.fit.parse.schedule.group;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ru.nsu.fit.parse.schedule.DayOfWeek;

import java.util.*;

public class ScheduleParser {
    private final List<Lesson>[][] lessons = new ArrayList[DAYS_IN_WEEK][LESSONS_IN_DAY];

    private static final int DAYS_IN_WEEK = 6;
    private static final int LESSONS_IN_DAY = 7;
    private static int GROUP_NUMBER;

    public void setGROUP_NUMBER(int GROUP_NUMBER){
        this.GROUP_NUMBER = GROUP_NUMBER;
    }

    public ScheduleParser() {
    }

    public ScheduleParser(int GROUP_NUMBER) {
        setGROUP_NUMBER(GROUP_NUMBER);
    }

    private String checkNullable(Element element) {
        return (element != null) ? element.text() : "";
    }

    private String getElementText(Elements elements, int k) {
        return checkNullable(elements.size() > k ? elements.get(k) : null);
    }

    public void parse() {
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
            System.out.println(DayOfWeek.fromNumber(i + 1).getName());
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
            System.out.print("\n");
        }
        System.out.println("/******************************************/");
    }
}