package ru.nsu.fit.schedule.impl.domain.model.data.entities;

import jakarta.persistence.*;
import lombok.*;
import ru.nsu.fit.group.impl.domain.model.data.entities.Group;
import ru.nsu.fit.student.impl.domain.model.data.entities.Student;

@Entity
@Table(name = "pinned_schedule")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PinnedSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;
}