package ru.nsu.fit.lesson.impl.domain.model.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Entity
@Table(name = "canceled_lesson")
@NoArgsConstructor
public class CanceledLesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "student_id")
    private Long studentId;
    @Column(name = "lesson_id")
    private Long lessonId;
    @Column(name = "date")
    private Date date;
}
