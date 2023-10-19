package ru.nsu.fit.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.*;

public class WebParser {
    private final Subject[][] subjects = new Subject[DAYS_IN_WEEK][LESSONS_IN_DAY];

    private static final int DAYS_IN_WEEK = 6;
    private static final int LESSONS_IN_DAY = 7;
    private static final int GROUP_NUMBER = 20209;

    public WebParser() {
        parse();
    }

    private List<String> getElementsText(Element currentCell, String selector) {
        List<String> elementsText = new ArrayList<>();
        Elements elements = currentCell.select(selector);
        for (Element element : elements) {
            elementsText.add((element != null) ? element.text() : "");
        }
        return elementsText;
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

                            List<String> title = getElementsText(currentCell, ".subject");
                            List<String> room = getElementsText(currentCell, ".room");
                            List<String> tutorName = getElementsText(currentCell, ".tutor");
                            List<String> type = getElementsText(currentCell, ".type");

                            if (tutorName.size() != title.size()) {
                                for (int k = 0; k < title.size() - tutorName.size(); k++) {
                                    tutorName.add("");
                                }
                            }

                            subjects[i][j] = new Subject(title, tutorName, room, type);
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
                for (int k = 0; k < subjects[i][j].getTitle().size(); k++) {
                    if (k > 0) {
                        System.out.print(" / ");
                    }
                    System.out.print(
                            subjects[i][j].getTitle().get(k) + " " +
                                    subjects[i][j].getType().get(k) + " " +
                                    subjects[i][j].getTutorName().get(k) + " " +
                                    subjects[i][j].getRoom().get(k));
                }
                System.out.print("\n");
            }
            System.out.println("/******************************************/");
        }
    }
}
