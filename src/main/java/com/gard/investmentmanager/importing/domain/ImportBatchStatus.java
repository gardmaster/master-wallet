package com.gard.investmentmanager.importing.domain;

public enum ImportBatchStatus {
    RECEIVED,
    PARSED,
    REVIEW_PENDING,
    CONFIRMED,
    CANCELED,
    FAILED
}