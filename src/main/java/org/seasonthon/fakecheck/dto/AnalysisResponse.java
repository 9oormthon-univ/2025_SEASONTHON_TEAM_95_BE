package org.seasonthon.fakecheck.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record AnalysisResponse(
        Long analysisId,
        String imageUrl,
        Double aiProbability,
        Double realProbability,
        String riskLevel,
        String conclusion,
        List<String> evidences
) {

    public AnalysisResponse withAnalysisIdAndRiskLevel(Long analysisId, String riskLevel) {
        return new AnalysisResponse(
                analysisId,
                this.imageUrl,
                this.aiProbability,
                this.realProbability,
                riskLevel,
                this.conclusion,
                this.evidences
        );
    }

}