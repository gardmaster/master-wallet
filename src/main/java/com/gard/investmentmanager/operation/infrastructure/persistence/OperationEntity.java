package com.gard.investmentmanager.operation.infrastructure.persistence;

import com.gard.investmentmanager.account.infrastructure.persistence.InvestmentAccountEntity;
import com.gard.investmentmanager.asset.infrastructure.persistence.AssetEntity;
import com.gard.investmentmanager.auth.infrastructure.persistence.UserEntity;
import com.gard.investmentmanager.operation.domain.OperationType;
import com.gard.investmentmanager.operation.domain.SourceType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "operations")
public class OperationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    public UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    public InvestmentAccountEntity account;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "asset_id", nullable = false)
    public AssetEntity asset;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type", nullable = false, length = 20)
    public OperationType operationType;

    @Column(name = "operation_date", nullable = false)
    public Instant operationDate;

    @Column(precision = 19, scale = 8)
    public BigDecimal quantity;

    @Column(name = "unit_price", precision = 19, scale = 8)
    public BigDecimal unitPrice;

    @Column(name = "gross_amount", precision = 19, scale = 8)
    public BigDecimal grossAmount;

    @Column(name = "net_amount", precision = 19, scale = 8)
    public BigDecimal netAmount;

    @Column(name = "fees_amount", precision = 19, scale = 8)
    public BigDecimal feesAmount;

    @Column(nullable = false, length = 3)
    public String currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_type", nullable = false, length = 20)
    public SourceType sourceType;

    @Column(name = "source_reference", length = 255)
    public String sourceReference;

    @Column(columnDefinition = "TEXT")
    public String notes;

    @Column(name = "created_at", nullable = false)
    public Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    public Instant updatedAt;
}