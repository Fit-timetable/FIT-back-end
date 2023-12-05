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
import java.util.List;


@Component
@Getter
@AllArgsConstructor
public class GroupParser {

    public static List<GroupDto> parse(String number) {
        List<GroupDto> groupDtos = new ArrayList<>();
        try {
            Document document = Jsoup.connect(GroupUrl.NSU_GROUP_LIST_URL).get();
            Elements scheduleElements = document.select("a.group");

            int counter = 0;
            for (Element scheduleElement : scheduleElements) {
                String groupNumber = scheduleElement.text();
                if (groupNumber.startsWith(number)) {
                    groupDtos.add(new GroupDto(null, groupNumber));
                }
                if (counter == scheduleElements.size() / 2) {
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return groupDtos;
    }
}
