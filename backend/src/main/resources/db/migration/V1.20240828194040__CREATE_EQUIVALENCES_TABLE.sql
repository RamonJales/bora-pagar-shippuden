CREATE TABLE subject_equivalent (
    subject_id BIGINT NOT NULL,
    equivalent_id BIGINT NOT NULL,
    FOREIGN KEY (subject_id) REFERENCES subject(id),
    FOREIGN KEY (equivalent_id) REFERENCES subject(id),
    CHECK (subject_id <> equivalent_id),
    CONSTRAINT subject_equivalent_unique UNIQUE (subject_id, equivalent_id)
);