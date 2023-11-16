CREATE TABLE pinned_schedule
(
    student_id BIGINT PRIMARY KEY REFERENCES student,
    group_id BIGINT NOT NULL REFERENCES "group"
);

