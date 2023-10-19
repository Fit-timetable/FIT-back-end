package ru.nsu.fit.parser;

import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Subject {
    private List<String> title;
    private List<String> tutorName;
    private List<String> room;
    private List<String> type;
}
