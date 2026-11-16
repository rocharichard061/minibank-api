
CREATE TABLE contas (
    id UUID PRIMARY KEY,
    titular VARCHAR(150) NOT NULL,
    email VARCHAR(255) NOT NULL,
    saldo NUMERIC(19,2) NOT NULL DEFAULT 0,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_contas_saldo_positivo
        CHECK (saldo >= 0),

    CONSTRAINT chk_contas_titular_nao_vazio
        CHECK (LENGTH(TRIM(titular)) > 0)
);

CREATE UNIQUE INDEX idx_contas_email_unique
    ON contas (LOWER(email));
