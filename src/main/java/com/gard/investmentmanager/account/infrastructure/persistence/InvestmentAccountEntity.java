package com.gard.investmentmanager.account.infrastructure.persistence;

import com.gard.investmentmanager.account.domain.AccountType;
import com.gard.investmentmanager.auth.infrastructure.persistence.UserEntity;
import com.gard.investmentmanager.institution.infrastructure.persistence.InstitutionEntity;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "investment_accounts")
public class InvestmentAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    public UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "institution_id", nullable = false)
    public InstitutionEntity institution;

    @Column(nullable = false, length = 150)
    public String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false, length = 30)
    public AccountType accountType;

    @Column(name = "base_currency", nullable = false, length = 3)
    public String baseCurrency;

    @Column(columnDefinition = "TEXT")
    public String notes;

    @Column(name = "created_at", nullable = false)
    public Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    public Instant updatedAt;
}