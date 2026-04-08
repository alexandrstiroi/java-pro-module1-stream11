CREATE TABLE IF NOT EXISTS users (
    id bigserial;
    username VARCHAR(255) unique not null
);