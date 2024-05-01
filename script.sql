CREATE TABLE "transaction"(
    id SERIAL PRIMARY KEY,
    customer_id INTEGER NOT NULL,
    valor INTEGER NOT NULL,
    tipo CHAR(1) NOT NULL,
    descricao VARCHAR(10) NOT NULL,
    realizada_em TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE balance(
    id SERIAL PRIMARY KEY,
    customer_id INTEGER NOT NULL,
    total INTEGER NOT NULL,
    limite INTEGER NOT NULL
);

INSERT INTO balance (id, customer_id, total, limite)
        VALUES (1, 1, 0, 1000 * 100),
               (2, 2, 0, 800 * 100),
               (3, 3, 0, 10000 * 100),
               (4, 4, 0, 100000 * 100),
               (5, 5, 0, 5000 * 100);

CREATE SEQUENCE transaction_seq START 1 INCREMENT 1;
ALTER TABLE "transaction" ALTER COLUMN id SET DEFAULT nextval('transaction_seq');

CREATE SEQUENCE balance_seq START 6 INCREMENT 1;
ALTER TABLE balance ALTER COLUMN id SET DEFAULT nextval('balance_seq');

