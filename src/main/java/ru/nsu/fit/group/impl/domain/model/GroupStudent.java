package ru.nsu.fit.group.impl.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.fit.student.impl.domain.model.Student;

@Entity
@Table(name = "group_student", uniqueConstraints = {@UniqueConstraint(columnNames = {"student_id", "group_id"})})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;
    @Column(nullable = false)
    private Boolean isModerator;
}
