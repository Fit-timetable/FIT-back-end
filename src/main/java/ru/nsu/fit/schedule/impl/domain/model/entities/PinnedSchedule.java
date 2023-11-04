package ru.nsu.fit.schedule.impl.domain.model.entities;

import jakarta.persistence.*;
import lombok.*;
import ru.nsu.fit.group.impl.domain.model.entities.Group;
import ru.nsu.fit.student.impl.domain.model.entities.Student;

@Entity
@Table(name = "pinned_schedule")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PinnedSchedule {
    @Id
    private Long id;
    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private Group group;
}