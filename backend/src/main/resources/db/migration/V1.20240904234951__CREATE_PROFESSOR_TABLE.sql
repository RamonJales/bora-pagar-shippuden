CREATE TABLE IF NOT EXISTS professor(
	id BIGSERIAL PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	siape_code VARCHAR(255) NOT NULL,
	image_url VARCHAR(255),
	department_id BIGINT REFERENCES department(id),
	created_at TIMESTAMPTZ,
	updated_at TIMESTAMPTZ,
	deleted_at TIMESTAMPTZ,
	UNIQUE(siape_code)
);
