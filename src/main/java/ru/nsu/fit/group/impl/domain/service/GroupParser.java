package ru.nsu.fit.group.impl.domain.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import ru.nsu.fit.group.api.GroupDto;
import ru.nsu.fit.group.port.GroupUrl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
@Getter
@AllArgsConstructor
public class GroupParser {

    public static List<GroupDto> parse(String number) {
        List<GroupDto> groupDtos = new ArrayList<>();
        try {
            Document document = Jsoup.connect(GroupUrl.NSU_GROUP_LIST_URL).get();
            Elements scheduleElements = document.select("a.group");

            Set<String> groupNumbers = new HashSet<>();

            for (Element scheduleElement : scheduleElements) {
                String groupNumber = scheduleElement.text();
                if (groupNumber.startsWith(number) && groupNumbers.add(groupNumber)) {
                    groupDtos.add(new GroupDto(null, groupNumber));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return groupDtos;
    }

    public static boolean doesGroupExist(String number) {
        try {
            Document document = Jsoup.connect(GroupUrl.NSU_GROUP_LIST_URL).get();
            Elements groupElements = document.select("a.group");
            return groupElements.stream().anyMatch(element -> element.text().equals(number));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
