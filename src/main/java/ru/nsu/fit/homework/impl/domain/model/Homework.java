package ru.nsu.fit.homework.impl.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "homework")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Homework {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "lesson_id")
    private Long lessonId;

    @Column(name = "deadline")
    private Instant deadline;

    @Column(name = "days_before_deadline_reminder")
    private Short daysBeforeDeadlineReminder;

    @Column(name = "estimated_time", nullable = false)
    private String estimatedTime;

}
