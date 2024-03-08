create table media
(
    id          BIGINT PRIMARY KEY,
    media_type  TINYINT      NOT NULL,
    media_url   VARCHAR(255) NOT NULL,
    thumbnail   boolean default false,
    sequence_id TINYINT      NOT NULL,
    item_id     BIGINT       NOT NULL
);