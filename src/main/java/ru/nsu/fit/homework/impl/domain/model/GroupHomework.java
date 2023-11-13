package ru.nsu.fit.homework.impl.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "group_homework")
public class GroupHomework {

    @Id
    @Column(name = "homework_id")
    private Long homeworkId;

    @Column(name = "group_id", nullable = false)
    private Long groupId;
}