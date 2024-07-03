CREATE TABLE subject (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(255) NOT NULL,
    program TEXT,
    deleted BOOLEAN DEFAULT FALSE,
    hours INT NOT NULL
);

CREATE TABLE subject_course (
    subject_id BIGINT,
    course_id BIGINT,
    level INT,
    subject_course_type VARCHAR(255),
    PRIMARY KEY (subject_id, course_id),
    FOREIGN KEY (subject_id) REFERENCES subject(id),
    FOREIGN KEY (course_id) REFERENCES course(id)
);