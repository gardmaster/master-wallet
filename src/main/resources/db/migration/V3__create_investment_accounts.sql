CREATE TABLE investment_accounts
(
    id             UUID PRIMARY KEY      DEFAULT gen_random_uuid(),
    user_id        UUID         NOT NULL REFERENCES users (id),
    institution_id UUID         NOT NULL REFERENCES institutions (id),
    name           VARCHAR(150) NOT NULL,
    account_type   VARCHAR(30)  NOT NULL,
    base_currency  VARCHAR(3)   NOT NULL,
    notes          TEXT,
    created_at     TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_investment_accounts_name_not_blank CHECK (btrim(name) <> ''),
    CONSTRAINT chk_investment_accounts_type CHECK (
        account_type IN ('BROKERAGE_ACCOUNT', 'BANK_ACCOUNT', 'TREASURY_ACCOUNT', 'OTHER')
        ),
    CONSTRAINT chk_investment_accounts_base_currency CHECK (
        char_length(base_currency) = 3 AND base_currency = upper(base_currency)
        )
);

CREATE INDEX idx_investment_accounts_user_id ON investment_accounts (user_id);
CREATE INDEX idx_investment_accounts_institution_id ON investment_accounts (institution_id);
CREATE UNIQUE INDEX uq_investment_accounts_user_institution_name
    ON investment_accounts (user_id, institution_id, name);