package ru.nsu.fit.lesson.impl.domain.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.fit.group.impl.domain.model.Group;
import ru.nsu.fit.lesson.impl.domain.model.LessonParity;
import ru.nsu.fit.lesson.impl.domain.model.LessonType;
import ru.nsu.fit.schedule.impl.domain.model.DayName;
import ru.nsu.fit.subject.impl.domain.model.Subject;

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

    public Lesson(Subject subject, LocalDateTime startTime, DayName dayName, String room, String meetLink, LessonType lessonType,
                  LessonParity lessonParity) {
        this.subject = subject;
        this.startTime = startTime;
        this.dayName = dayName;
        this.room = room;
        this.meetLink = meetLink;
        this.lessonType = lessonType;
        this.lessonParity = lessonParity;

    }

    public void update(Subject subject, LocalDateTime startTime, DayName dayName,
                       String teacher, String room, String meetLink) {
        this.subject = subject;
        this.startTime = startTime;
        this.dayName = dayName;
        this.teacher = teacher;
        this.room = room;
        this.meetLink = meetLink;
    }
}
