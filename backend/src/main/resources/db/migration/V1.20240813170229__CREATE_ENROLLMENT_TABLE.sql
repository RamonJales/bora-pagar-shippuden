CREATE TABLE enrollment (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    classroom_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (classroom_id) REFERENCES classroom(id)
);