package ru.nsu.fit.homework.impl.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.time.Duration;
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
    @Type(value = Interval.class)
    private Duration estimatedTime;

    @Column(name = "homework_text", nullable = false)
    private String homeworkText;

    @Column(name = "notification_period")
    @Type(value = Interval.class)
    private Duration notificationPeriod;
}
