CREATE TABLE address (
 id BIGINT PRIMARY KEY,
 user_id BIGINT,
 line1 text,
 line2 text,
 city text,
 state text,
 country VARCHAR(100),
 postal_code INT
);
