ALTER TABLE institutions
    ADD COLUMN deleted_at TIMESTAMPTZ NULL;

ALTER TABLE investment_accounts
    ADD COLUMN deleted_at TIMESTAMPTZ NULL;

ALTER TABLE assets
    ADD COLUMN deleted_at TIMESTAMPTZ NULL;

DROP INDEX IF EXISTS uq_institutions_user_name;
CREATE UNIQUE INDEX uq_institutions_user_name
    ON institutions (user_id, name) WHERE deleted_at IS NULL;

DROP INDEX IF EXISTS uq_investment_accounts_user_institution_name;
CREATE UNIQUE INDEX uq_investment_accounts_user_institution_name
    ON investment_accounts (user_id, institution_id, name) WHERE deleted_at IS NULL;

DROP INDEX IF EXISTS uq_assets_user_asset_type_ticker;
CREATE UNIQUE INDEX uq_assets_user_asset_type_ticker
    ON assets (user_id, asset_type, ticker) WHERE ticker IS NOT NULL AND deleted_at IS NULL;