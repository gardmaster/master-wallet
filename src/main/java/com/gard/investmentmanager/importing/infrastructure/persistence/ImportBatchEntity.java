package com.gard.investmentmanager.importing.infrastructure.persistence;

import com.gard.investmentmanager.auth.infrastructure.persistence.UserEntity;
import com.gard.investmentmanager.importing.domain.ImportBatchStatus;
import com.gard.investmentmanager.importing.domain.ImportBatchType;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "import_batches")
public class ImportBatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    public UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(name = "batch_type", nullable = false, length = 50)
    public ImportBatchType batchType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    public ImportBatchStatus status;

    @Column(name = "created_at", nullable = false)
    public Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    public Instant updatedAt;

    @Column(name = "confirmed_at")
    public Instant confirmedAt;

    @Column(name = "canceled_at")
    public Instant canceledAt;

    @Column(name = "failure_reason", columnDefinition = "TEXT")
    public String failureReason;
}