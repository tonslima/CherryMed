CREATE TABLE users (
    id          BIGSERIAL       PRIMARY KEY,
    login       VARCHAR(100)    NOT NULL UNIQUE,
    password    VARCHAR(100)    NOT NULL,
    role        VARCHAR(50)     NOT NULL
);