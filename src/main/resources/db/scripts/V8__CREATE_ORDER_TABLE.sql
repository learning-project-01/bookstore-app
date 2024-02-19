CREATE TABLE orders
(
    order_id         BIGINT PRIMARY KEY,
    customer_id      BIGINT,
    address_id BIGINT,
    order_time       TIMESTAMP,
    total_amount     DECIMAL(10, 2),
    shipping_address text
);
