CREATE TABLE cart_item (
  id BIGINT PRIMARY KEY,
  cart_id BIGINT,
  catalog_item_id BIGINT,
  quantity INT,
  state INT
);
