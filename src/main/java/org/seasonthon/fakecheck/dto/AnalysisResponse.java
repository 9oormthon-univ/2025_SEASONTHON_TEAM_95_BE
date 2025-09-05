package org.seasonthon.fakecheck.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record AnalysisResponse(
        Long analysisId,
        String imageUrl,
        Double aiProbability,
        Double realProbability,
        String conclusion,
        List<String> evidences
) {

    // analysisId를 추가한 새로운 인스턴스 반환
    public AnalysisResponse withAnalysisId(Long analysisId) {
        return new AnalysisResponse(
                analysisId,
                this.imageUrl,
                this.aiProbability,
                this.realProbability,
                this.conclusion,
                this.evidences
        );
    }
}
