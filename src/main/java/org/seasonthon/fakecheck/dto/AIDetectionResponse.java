package org.seasonthon.fakecheck.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;

@Builder
public record AIDetectionResponse(
        String status,
        @JsonProperty("image_url") String imageUrl,
        Results results
) {
    @Builder
    public record Results(
            @JsonProperty("cross_validation") CrossValidation crossValidation,
            @JsonProperty("final_decision") String finalDecision,
            String confidence,
            String conclusion,
            List<String> evidence
    ) {}

    @Builder
    public record CrossValidation(
            @JsonProperty("AI") Double aiPercentage,      // "AI"로 변경
            @JsonProperty("Real") Double realPercentage   // "Real"로 변경
    ) {}
}

