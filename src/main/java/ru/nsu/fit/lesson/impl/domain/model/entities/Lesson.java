package ru.nsu.fit.lesson.impl.domain.model.entities;

import lombok.*;
import jakarta.persistence.*;
import ru.nsu.fit.group.impl.domain.model.entities.Group;
import ru.nsu.fit.schedule.impl.domain.model.DayName;
import ru.nsu.fit.lesson.impl.domain.model.LessonParity;
import ru.nsu.fit.lesson.impl.domain.model.LessonType;
import ru.nsu.fit.subject.impl.domain.model.entities.Subject;

import java.time.LocalDateTime;

@Entity
@Table(name = "lesson")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "day_name", nullable = false)
    private DayName dayName;
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;
    @Column(name = "room")
    private String room;
    @Column(name = "teacher")
    private String teacher;
    @Column(name = "meet_link")
    private String meetLink;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private LessonType lessonType;
    @Enumerated(EnumType.STRING)
    @Column(name = "parity", nullable = false)
    private LessonParity lessonParity;
    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
}
