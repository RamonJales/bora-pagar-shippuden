CREATE TYPE course_level_enum AS ENUM ('GRADUATION', 'TECHNICAL');
CREATE TABLE course (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    coordinator VARCHAR(255) NOT NULL,
    course_level course_level_enum NOT NULL,
    deleted BOOLEAN DEFAULT FALSE
);