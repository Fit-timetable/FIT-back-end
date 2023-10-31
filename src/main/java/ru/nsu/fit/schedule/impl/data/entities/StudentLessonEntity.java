package ru.nsu.fit.schedule.impl.data.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student_lesson", uniqueConstraints = {@UniqueConstraint(columnNames = {"student_id", "lesson_id"})})
public class StudentLessonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private StudentEntity studentEntity;
    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private LessonEntity lessonEntity;
    @Column(name = "is_visited", nullable = false)
    private boolean visited;
}
