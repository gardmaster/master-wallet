CREATE
EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE users
(
    id                                UUID PRIMARY KEY      DEFAULT gen_random_uuid(),
    name                              VARCHAR(150) NOT NULL,
    email                             VARCHAR(255) NOT NULL,
    password_hash_or_external_auth_id VARCHAR(255) NOT NULL,
    created_at                        TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                        TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uq_users_email UNIQUE (email),
    CONSTRAINT chk_users_name_not_blank CHECK (btrim(name) <> ''),
    CONSTRAINT chk_users_email_not_blank CHECK (btrim(email) <> ''),
    CONSTRAINT chk_users_auth_not_blank CHECK (btrim(password_hash_or_external_auth_id) <> '')
);