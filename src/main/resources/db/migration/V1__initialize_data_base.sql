CREATE TABLE student
(
    id         BIGSERIAL PRIMARY KEY,
    email      VARCHAR NOT NULL UNIQUE,
    "password" VARCHAR NOT NULL CHECK ( length("password") >= 6 )
);

CREATE TABLE global_moderator
(
    student_id BIGINT PRIMARY KEY REFERENCES student
);

CREATE TABLE "group"
(
    id     BIGSERIAL PRIMARY KEY,
    number VARCHAR NOT NULL UNIQUE
);

CREATE TABLE group_student
(
    id           BIGSERIAL PRIMARY KEY,
    student_id   BIGINT REFERENCES student NOT NULL,
    group_id     BIGINT REFERENCES "group" NOT NULL,
    is_moderator BOOLEAN                   NOT NULL,
    UNIQUE (student_id, group_id)
);

CREATE TABLE subject
(
    id     BIGSERIAL PRIMARY KEY,
    "name" VARCHAR NOT NULL UNIQUE
);

CREATE TABLE resource
(
    id            BIGSERIAL PRIMARY KEY,
    "name"        VARCHAR                   NOT NULL,
    description   VARCHAR,
    status        VARCHAR                   NOT NULL,
    deletion_date TIMESTAMP WITH TIME ZONE,
    group_id      BIGINT REFERENCES "group",
    subject_id    BIGINT REFERENCES subject NOT NULL
);

CREATE TABLE resource_link_material
(
    resource_id BIGINT PRIMARY KEY REFERENCES resource,
    link        VARCHAR
);

CREATE TABLE resource_drive_material
(
    resource_id    BIGINT PRIMARY KEY REFERENCES resource,
    file_drive_uri VARCHAR
);

CREATE TABLE lesson
(
    id         BIGSERIAL PRIMARY KEY,
    day_name   VARCHAR                   NOT NULL,
    start_time TIMESTAMP WITH TIME ZONE  NOT NULL,
    room       VARCHAR,
    teacher    VARCHAR,
    meet_link  VARCHAR,
    "type"     VARCHAR                   NOT NULL,
    parity     VARCHAR                   NOT NULL,
    subject_id BIGINT REFERENCES subject NOT NULL,
    group_id   BIGINT REFERENCES "group"
);

CREATE TABLE student_lesson
(
    id         BIGSERIAL PRIMARY KEY,
    student_id BIGINT REFERENCES student NOT NULL,
    lesson_id  BIGINT REFERENCES lesson  NOT NULL,
    is_visited BOOLEAN                   NOT NULL,
    UNIQUE (student_id, lesson_id)
);

CREATE TABLE canceled_lesson
(
    id         BIGSERIAL PRIMARY KEY,
    student_id BIGINT REFERENCES student NOT NULL,
    lesson_id  BIGINT REFERENCES lesson  NOT NULL,
    "date"     TIMESTAMP WITH TIME ZONE  NOT NULL
);

CREATE TABLE homework
(
    id                            BIGSERIAL PRIMARY KEY,
    student_id                    BIGINT REFERENCES student NOT NULL,
    lesson_id                     BIGINT REFERENCES lesson,
    deadline                      TIMESTAMP WITH TIME ZONE,
    days_before_deadline_reminder SMALLINT,
    estimated_time                INTERVAL
);

CREATE TABLE homework_file
(
    id             BIGSERIAL PRIMARY KEY      NOT NULL,
    homework_id    BIGINT REFERENCES homework NOT NULL,
    file_drive_uri VARCHAR                    NOT NULL UNIQUE
);

CREATE TABLE group_homework
(
    homework_id BIGINT PRIMARY KEY REFERENCES homework,
    group_id    BIGINT REFERENCES "group" NOT NULL
);