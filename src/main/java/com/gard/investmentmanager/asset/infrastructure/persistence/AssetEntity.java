package com.gard.investmentmanager.asset.infrastructure.persistence;

import com.gard.investmentmanager.asset.domain.AssetType;
import com.gard.investmentmanager.auth.infrastructure.persistence.UserEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;

@Entity
@Table(name = "assets")
public class AssetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    public UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(name = "asset_type", nullable = false, length = 30)
    public AssetType assetType;

    @Column(nullable = false, length = 200)
    public String name;

    @Column(length = 30)
    public String ticker;

    @Column(length = 2)
    public String country;

    @Column(nullable = false, length = 3)
    public String currency;

    @Column(length = 150)
    public String issuer;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata_json", columnDefinition = "jsonb")
    public String metadataJson;

    @Column(name = "created_at", nullable = false)
    public Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    public Instant updatedAt;
}