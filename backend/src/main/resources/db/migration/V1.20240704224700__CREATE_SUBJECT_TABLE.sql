CREATE TABLE subject (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(255) NOT NULL,
    syllabus TEXT,
    deleted BOOLEAN DEFAULT FALSE,
    hours INT NOT NULL
);