package com.gard.investmentmanager.importing.infrastructure.persistence;

import com.gard.investmentmanager.auth.infrastructure.persistence.UserEntity;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "imported_files")
public class ImportedFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "batch_id", nullable = false)
    public ImportBatchEntity batch;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    public UserEntity user;

    @Column(name = "original_file_name", nullable = false, length = 255)
    public String originalFileName;

    @Column(name = "storage_path_or_reference", nullable = false, length = 500)
    public String storagePathOrReference;

    @Column(name = "content_type", nullable = false, length = 100)
    public String contentType;

    @Column(name = "file_hash", nullable = false, length = 128)
    public String fileHash;

    @Column(name = "uploaded_at", nullable = false)
    public Instant uploadedAt;
}