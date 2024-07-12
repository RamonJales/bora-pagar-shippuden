CREATE TABLE IF NOT EXISTS subject_course (
    course_id BIGINT NOT NULL,
    subject_id BIGINT NOT NULL,
    expected_semester INTEGER,
    subject_course_type VARCHAR(255),

    CONSTRAINT fk_course
        FOREIGN KEY (course_id)
        REFERENCES course (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_subject
        FOREIGN KEY (subject_id)
        REFERENCES subject (id)
        ON DELETE CASCADE
);