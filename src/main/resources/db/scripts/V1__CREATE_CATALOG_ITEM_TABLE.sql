CREATE TABLE catalog_item (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    price FLOAT
);

INSERT INTO catalog_item (id, name, price) VALUES
(1, 'Product A', 19.99),
(2, 'Product B', 29.99),
(3, 'Product C', 39.99),
(4, 'Product D', 49.99);