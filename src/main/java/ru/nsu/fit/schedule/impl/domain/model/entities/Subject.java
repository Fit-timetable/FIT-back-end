package ru.nsu.fit.schedule.impl.domain.model.entities;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "subject")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
}
