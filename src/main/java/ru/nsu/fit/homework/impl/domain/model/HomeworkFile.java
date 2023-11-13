package ru.nsu.fit.homework.impl.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "homework")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HomeworkFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "homework_id", nullable = false)
    private Long homeworkId;

    @Column(name = "file_drive_uri", nullable = false, unique = true)
    private String fileDriveUri;
}
