package com.gard.investmentmanager.institution.infrastructure.persistence;

import com.gard.investmentmanager.auth.infrastructure.persistence.UserEntity;
import com.gard.investmentmanager.institution.domain.InstitutionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "institutions")
public class InstitutionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    public UserEntity user;

    @Column(nullable = false, length = 150)
    public String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "institution_type", nullable = false, length = 30)
    public InstitutionType institutionType;

    @Column(columnDefinition = "TEXT")
    public String notes;

    @Column(name = "created_at", nullable = false)
    public Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    public Instant updatedAt;

    @Column(name = "deleted_at")
    public Instant deletedAt;
}