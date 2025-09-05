package org.seasonthon.fakecheck.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.seasonthon.fakecheck.BaseEntity;

@Getter
@Builder
@AllArgsConstructor(access =  AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class AnalysisEvidence extends BaseEntity {

    @Column(nullable = false)
    private Long analysisId;

    @Column(nullable = false, length = 1000)
    private String description;

    public static AnalysisEvidence create(Long analysisId, String description) {
        return AnalysisEvidence.builder()
                .analysisId(analysisId)
                .description(description)
                .build();
    }

}