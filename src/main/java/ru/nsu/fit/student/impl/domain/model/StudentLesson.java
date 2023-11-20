package ru.nsu.fit.student.impl.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.fit.lesson.impl.domain.model.entities.Lesson;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student_lesson", uniqueConstraints = {@UniqueConstraint(columnNames = {"student_id", "lesson_id"})})
public class StudentLesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;
    @Column(name = "is_visited", nullable = false)
    private Boolean visited;

    public StudentLesson(Lesson lesson, Student student, Boolean visited) {
        this.lesson = lesson;
        this.student = student;
        this.visited = visited;
    }
}
