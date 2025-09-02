package org.seasonthon.fakecheck.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.seasonthon.fakecheck.enums.RiskLevel;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Analysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double fakeProbability;

    @Column(nullable = false)
    private String s3Url;

    @Enumerated(EnumType.STRING)
    private RiskLevel riskLevel;

    @Column(nullable = false)
    private LocalDateTime createTime;

    @Builder(access = AccessLevel.PRIVATE)
    private Analysis(
            Double fakeProbability,
            String s3Url,
            RiskLevel riskLevel,
            LocalDateTime createTime
    ) {
        this.fakeProbability = fakeProbability;
        this.s3Url = s3Url;
        this.riskLevel = riskLevel;
        this.createTime = createTime;
    }

    public static Analysis create(String s3Url, Double fakeProbability) {
        return Analysis.builder()
                .fakeProbability(fakeProbability)
                .s3Url(s3Url)
                .riskLevel(RiskLevel.fromProbability(fakeProbability))
                .createTime(LocalDateTime.now())
                .build();
    }

}