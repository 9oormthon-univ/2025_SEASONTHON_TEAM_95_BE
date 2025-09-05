package org.seasonthon.fakecheck.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.seasonthon.fakecheck.BaseEntity;
import org.seasonthon.fakecheck.enums.RiskLevel;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Analysis extends BaseEntity {

    @Column(nullable = false)
    private Double aiProbability;

    @Column(nullable = false)
    private Double realProbability;

    @Column(nullable = false)
    private String s3Url;

    @Column(nullable = false)
    private String conclusion;

    @Enumerated(EnumType.STRING)
    private RiskLevel riskLevel;

    public static Analysis create(String s3Url, Double aiProbability, Double realProbability, String conclusion) {
        return Analysis.builder()
                .aiProbability(aiProbability)
                .realProbability(realProbability)
                .conclusion(conclusion)
                .s3Url(s3Url)
                .riskLevel(RiskLevel.fromProbability(realProbability))
                .build();
    }

}