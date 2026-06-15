package com.gard.investmentmanager.importing.infrastructure.persistence;

import com.gard.investmentmanager.account.infrastructure.persistence.InvestmentAccountEntity;
import com.gard.investmentmanager.asset.infrastructure.persistence.AssetEntity;
import com.gard.investmentmanager.auth.infrastructure.persistence.UserEntity;
import com.gard.investmentmanager.importing.domain.ImportedDraftStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "imported_operation_drafts")
public class ImportedOperationDraftEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "batch_id", nullable = false)
    public ImportBatchEntity batch;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "imported_file_id", nullable = false)
    public ImportedFileEntity importedFile;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    public UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    public InvestmentAccountEntity account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id")
    public AssetEntity asset;

    @Column(name = "raw_asset_name", length = 255)
    public String rawAssetName;

    @Column(name = "raw_operation_type", length = 50)
    public String rawOperationType;

    @Column(name = "operation_date")
    public Instant operationDate;

    @Column(precision = 19, scale = 8)
    public BigDecimal quantity;

    @Column(name = "unit_price", precision = 19, scale = 8)
    public BigDecimal unitPrice;

    @Column(name = "gross_amount", precision = 19, scale = 8)
    public BigDecimal grossAmount;

    @Column(name = "fees_amount", precision = 19, scale = 8)
    public BigDecimal feesAmount;

    @Column(length = 3)
    public String currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    public ImportedDraftStatus status;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "validation_errors", columnDefinition = "jsonb")
    public String validationErrors;

    @Column(name = "created_at", nullable = false)
    public Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    public Instant updatedAt;
}