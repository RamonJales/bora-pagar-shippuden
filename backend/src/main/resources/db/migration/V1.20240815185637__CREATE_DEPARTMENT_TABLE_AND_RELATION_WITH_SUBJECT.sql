CREATE TABLE department (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    deleted BOOLEAN DEFAULT FALSE
);

ALTER TABLE subject ADD COLUMN department_id BIGINT;
ALTER TABLE subject ADD CONSTRAINT fk_department_id FOREIGN KEY (department_id) REFERENCES department(id);