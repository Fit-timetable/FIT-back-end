package ru.nsu.fit.group.impl.domain.model.data.entities;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "\"group\"")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String number;
}