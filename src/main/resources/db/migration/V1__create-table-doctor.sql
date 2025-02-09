CREATE TABLE doctor (
    id              BIGSERIAL       PRIMARY KEY,
    name            VARCHAR(100)    NOT NULL,
    email           VARCHAR(100)    NOT NULL UNIQUE,
    phone           VARCHAR(50)     NOT NULL,
    crm             VARCHAR(50)     NOT NULL UNIQUE,
    specialty       VARCHAR(100)    NOT NULL,
    street          VARCHAR(100)    NOT NULL,
    number          VARCHAR(50),
    complement      VARCHAR(100),
    neighborhood    VARCHAR(100)    NOT NULL,
    city            VARCHAR(100)    NOT NULL,
    uf              CHAR(2)         NOT NULL,
    postal_code     VARCHAR(20)     NOT NULL,
    country         VARCHAR(100)    NOT NULL,
    active          BOOLEAN         NOT NULL
);