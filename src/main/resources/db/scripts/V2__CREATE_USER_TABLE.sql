
CREATE TABLE user (
    id BIGINT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    gender VARCHAR(10),
    role VARCHAR(20),
    status BOOLEAN
);
