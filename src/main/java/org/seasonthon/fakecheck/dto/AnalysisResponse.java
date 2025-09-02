package org.seasonthon.fakecheck.dto;

import lombok.Builder;

@Builder
public record AnalysisResponse(
        Long analysisId,
        String imageUrl,
        Double fakeProbability,
        String riskLevel
) {

}