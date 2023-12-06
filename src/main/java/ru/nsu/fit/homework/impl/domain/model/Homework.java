package ru.nsu.fit.homework.impl.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.ZonedDateTime;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "homework")
@Data
@NoArgsConstructor
public class Homework {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "lesson_id")
    private Long lessonId;

    @Column(name = "deadline")
    private ZonedDateTime deadline;

    @Column(name = "days_before_deadline_reminder")
    private Short daysBeforeDeadlineReminder;

    @Column(name = "estimated_time", nullable = false, columnDefinition = "interval")
    @Type(value = Interval.class)
    private Duration estimatedTime;

    @Column(name = "homework_text", nullable = false)
    private String homeworkText;

    @Column(name = "notification_period")
    @Type(value = Interval.class)
    private Duration notificationPeriod;

    public Long getStudentId(){
        return studentId;
    }

    public Long getLessonId(){
        return lessonId;
    }

    public ZonedDateTime getDeadline(){
        return deadline;
    }

    public Short getDaysBeforeDeadlineReminder(){
        return daysBeforeDeadlineReminder;
    }

    public Duration getEstimatedTime(){
        return estimatedTime;
    }

    public String getHomeworkText() {
        return homeworkText;
    }

    public Duration getNotificationPeriod() {
        return notificationPeriod;
    }
}
