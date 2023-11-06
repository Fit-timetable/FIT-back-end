package ru.nsu.fit.schedule.impl.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.fit.group.impl.domain.model.Group;
import ru.nsu.fit.student.impl.domain.model.Student;

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