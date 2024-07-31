CREATE TABLE classroom (
    id BIGSERIAL NOT NULL,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    subject_id BIGSERIAL NOT NULL,
    vacancies INTEGER NOT NULL,
    location VARCHAR NOT NULL,
    year_period VARCHAR NOT NULL,

    PRIMARY KEY(id),
    CONSTRAINT fk_subject
        FOREIGN KEY (subject_id)
        REFERENCES subject (id)
        ON DELETE CASCADE
);