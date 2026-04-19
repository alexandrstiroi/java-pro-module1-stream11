CREATE TABLE IF NOT EXISTS products (
    id BIGSERIAL PRIMARY KEY,
    account_number VARCHAR(255) NOT NULL,
    balance NUMERIC,
    product_type VARCHAR(50),
    user_id BIGINT REFERENCES users(id)
);