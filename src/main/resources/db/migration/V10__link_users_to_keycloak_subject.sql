ALTER TABLE users
    ALTER COLUMN password_hash_or_external_auth_id DROP NOT NULL;

ALTER TABLE users
    ADD COLUMN external_subject VARCHAR(255);

CREATE UNIQUE INDEX uq_users_external_subject
    ON users (external_subject) WHERE external_subject IS NOT NULL;