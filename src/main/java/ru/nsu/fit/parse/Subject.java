package ru.nsu.fit.parse;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class Subject {
    private String title;
    private String tutorName;
    private String room;
    private String type;
    private String week;
}
