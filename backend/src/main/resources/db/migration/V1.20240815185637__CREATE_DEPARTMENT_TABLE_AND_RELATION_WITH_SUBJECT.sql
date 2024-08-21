CREATE TABLE department (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    link VARCHAR(255) NOT NULL,
    code INT NOT NULL,
    deleted_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ,
    updated_at TIMESTAMPTZ
);

ALTER TABLE subject ADD COLUMN department_id BIGINT;
ALTER TABLE subject ADD CONSTRAINT fk_department_id FOREIGN KEY (department_id) REFERENCES department(id);