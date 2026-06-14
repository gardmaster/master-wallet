CREATE TABLE imported_files
(
    id                        UUID PRIMARY KEY      DEFAULT gen_random_uuid(),
    batch_id                  UUID         NOT NULL REFERENCES import_batches (id),
    user_id                   UUID         NOT NULL REFERENCES users (id),
    original_file_name        VARCHAR(255) NOT NULL,
    storage_path_or_reference VARCHAR(500) NOT NULL,
    content_type              VARCHAR(100) NOT NULL,
    file_hash                 VARCHAR(128) NOT NULL,
    uploaded_at               TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_imported_files_original_name_not_blank CHECK (btrim(original_file_name) <> ''),
    CONSTRAINT chk_imported_files_storage_path_not_blank CHECK (btrim(storage_path_or_reference) <> ''),
    CONSTRAINT chk_imported_files_content_type_not_blank CHECK (btrim(content_type) <> ''),
    CONSTRAINT chk_imported_files_file_hash_not_blank CHECK (btrim(file_hash) <> '')
);

CREATE INDEX idx_imported_files_batch_id ON imported_files (batch_id);
CREATE INDEX idx_imported_files_user_id ON imported_files (user_id);
CREATE UNIQUE INDEX uq_imported_files_batch_file_hash ON imported_files (batch_id, file_hash);