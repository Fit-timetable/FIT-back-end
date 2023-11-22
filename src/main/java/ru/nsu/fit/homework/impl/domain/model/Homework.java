package ru.nsu.fit.homework.impl.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.time.ZonedDateTime;
import java.time.Duration;

@Entity
@Table(name = "homework")
@Data
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
    private ZonedDateTime deadline;

    @Column(name = "days_before_deadline_reminder")
    private Short daysBeforeDeadlineReminder;

    @Column(name = "estimated_time", nullable = false)
    private Duration estimatedTime;

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
}
