package ru.nsu.fit.parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.*;

public class WebParser {
    private final List<Subject>[][] subjects = new ArrayList[DAYS_IN_WEEK][LESSONS_IN_DAY];

    private static final int DAYS_IN_WEEK = 6;
    private static final int LESSONS_IN_DAY = 7;
    private static final int GROUP_NUMBER = 20209;

    public WebParser() {
        parse();
    }

    private String checkNullable(Element element) {
        return (element != null) ? element.text() : "";
    }

    private String getElementText(Elements elements, int k) {
        return checkNullable(elements.size() > k ? elements.get(k) : null);
    }

    private void parse() {
        try {
            Document document = Jsoup.connect("https://table.nsu.ru/group/" + GROUP_NUMBER).get();

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

                            subjects[i][j] = new ArrayList<>();
                            for (int k = 0; k < currentCell.select(".subject").size(); k++) {
                                String subject = getElementText(currentCell.select(".subject"), k);
                                String tutor = getElementText(currentCell.select(".tutor"), k);
                                String room = getElementText(currentCell.select(".room"), k);
                                String type = getElementText(currentCell.select(".type"), k);
                                String week = getElementText(currentCell.select(".week"), k);

                                subjects[i][j].add(new Subject(subject, tutor, room, type, week));
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
                for (int k = 0; k < subjects[i][j].size(); k++) {
                    if (k > 0) {
                        System.out.print(" / ");
                    }
                    System.out.print(subjects[i][j].get(k).getTitle() + " " +
                                    subjects[i][j].get(k).getType() + " " +
                                    subjects[i][j].get(k).getTutorName() + " " +
                                    subjects[i][j].get(k).getRoom() + " " +
                                    subjects[i][j].get(k).getWeek());
                }
                System.out.print("\n");
            }
            System.out.println("/******************************************/");
        }
    }
}
