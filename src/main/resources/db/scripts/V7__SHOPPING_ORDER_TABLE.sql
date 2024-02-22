CREATE TABLE shopping_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    total_amount FLOAT NOT NULL,
    total_item_count INT NOT NULL,
    order_date DATE NOT NULL,
    address VARCHAR(255) NOT NULL
);

CREATE TABLE order_item (
    id BIGINT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    catalog_item_id BIGINT NOT NULL,
    cart_id BIGINT,
    quantity INT NOT NULL,
    unit_price FLOAT NOT NULL,
    total FLOAT NOT NULL,
    purchased_on DATE NOT NULL,
    status_code INT(32) NOT NULL
);
