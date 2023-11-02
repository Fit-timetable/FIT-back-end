CREATE TABLE pinned_schedule
(
    student_id BIGINT NOT NULL REFERENCES student,
    group_id BIGINT NOT NULL REFERENCES "group",
    PRIMARY KEY (student_id, group_id)
);

