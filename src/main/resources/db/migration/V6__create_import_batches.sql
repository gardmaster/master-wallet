CREATE TABLE import_batches
(
    id             UUID PRIMARY KEY     DEFAULT gen_random_uuid(),
    user_id        UUID        NOT NULL REFERENCES users (id),
    batch_type     VARCHAR(50) NOT NULL,
    status         VARCHAR(30) NOT NULL,
    created_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    confirmed_at   TIMESTAMPTZ,
    canceled_at    TIMESTAMPTZ,
    failure_reason TEXT,

    CONSTRAINT chk_import_batches_type_not_blank CHECK (btrim(batch_type) <> ''),
    CONSTRAINT chk_import_batches_status CHECK (
        status IN ('RECEIVED', 'PARSED', 'REVIEW_PENDING', 'CONFIRMED', 'CANCELED', 'FAILED')
        ),
    CONSTRAINT chk_import_batches_failure_reason_not_blank CHECK (
        failure_reason IS NULL OR btrim(failure_reason) <> ''
        )
);

CREATE INDEX idx_import_batches_user_id ON import_batches (user_id);
CREATE INDEX idx_import_batches_status ON import_batches (status);