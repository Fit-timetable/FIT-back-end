package ru.nsu.fit.schedule.impl.data.entities;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "lesson")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "day_name", nullable = false)
    private String dayName;
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;
    @Column(name = "room")
    private String room;
    @Column(name = "teacher")
    private String teacher;
    @Column(name = "meet_link")
    private String meetLink;
    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "parity", nullable = false)
    private String parity;
    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private SubjectEntity subjectEntity;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupEntity groupEntity;
}
